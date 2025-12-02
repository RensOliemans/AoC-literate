#!/usr/bin/env sh
":"; # -*- mode: emacs-lisp; lexical-binding: t; -*-
":"; exec emacs --no-init-file --script "$0" -- "$@" # -*- mode: emacs-lisp; lexical-binding: t; -*-

(push "~/org-mode/lisp/" load-path)
(setq load-prefer-newer t)
(require 'ox-html)
(require 'htmlize)

(package-refresh-contents)
(package-install 'htmlize)

;; (push '(:eval . "no-export") org-babel-default-header-args)
;; (push '(:eval . "no-export") org-babel-default-inline-header-args)

(setq org-confirm-babel-evaluate nil
      ;; org-html-style-default ""
      ;; org-html-scripts ""
      org-html-htmlize-output-type 'css
      org-html-doctype "html5"
      org-html-html5-fancy t
      org-html-validation-link nil
      org-html-preamble
      (with-temp-buffer (insert-file-contents "preamble.html") (buffer-string))
      org-html-postamble ""
      org-html-head
      "<link rel=\"stylesheet\" title=\"Standard\" href=\"/main.css\" type=\"text/css\" />
<link rel=\"stylesheet\" title=\"Standard\" href=\"/aoc/style/aoc.css\" type=\"text/css\" />
<link rel=\"icon\" href=\"/favicon.ico\" type=\"image/x-icon\" />")


(dolist (org-file (cl-remove-if
		   (lambda (n) (string-match-p "./archive/" n))
		   (directory-files-recursively default-directory "\\.org$")))
  (let ((html-file (concat (file-name-directory org-file)
			   (file-name-base org-file) ".html")))
    (if (and (file-exists-p html-file)
	     (file-newer-than-file-p html-file org-file)
             ;; If there are include files or code, we need to
             ;; re-generate the HTML just in case if the included
             ;; files are changed.
             (with-temp-buffer
               (insert-file-contents org-file)
               (and
		(save-excursion
		  (goto-char (point-min))
		  (not (re-search-forward "#\\+include:" nil t)))
                (save-excursion
		  (goto-char (point-min))
		  (not (re-search-forward "#\\+begin_src" nil t))))))
	(message " [skipping] unchanged %s" org-file)
      (message "[exporting] %s" (file-relative-name org-file default-directory))
      (with-current-buffer (find-file org-file)
	(org-html-export-to-html)))))
