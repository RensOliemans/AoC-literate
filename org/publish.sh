#!/usr/bin/env sh
":"; # -*- mode: emacs-lisp; lexical-binding: t; -*-
":"; exec emacs --no-init-file --script "$0" -- "$@" # -*- mode: emacs-lisp; lexical-binding: t; -*-

(defvar worg-publish-stop-on-error (member "--debug" command-line-args)
  "When non-nil, stop publishing process when an error is encountered.
This variable can be set when running publish.sh script:
  ./publish.sh --debug")

(push "~/org-mode/lisp/" load-path)
(setq load-prefer-newer t)
(require 'ox-html)
(require 'cl-seq)
(require 'htmlize)
(require 'org-inlinetask)

(setq make-backup-files nil
      debug-on-error t)

(push '(:eval . "no-export") org-babel-default-header-args)
(push '(:eval . "no-export") org-babel-default-inline-header-args)

;; FIXME: Working around ESS bug.  `font-lock-reference-face' has been removed in Emacs 29.
(define-obsolete-variable-alias
  'font-lock-reference-face 'font-lock-constant-face "20.3")

(setq org-confirm-babel-evaluate nil
      ess-ask-for-ess-directory nil
      ess-startup-directory nil
      org-html-style-default ""
      org-html-scripts ""
      org-html-htmlize-output-type 'css
      org-html-doctype "html5"
      org-html-html5-fancy t
      org-html-validation-link nil
      org-plantuml-jar-path "/usr/share/plantuml/plantuml.jar"
      org-ditaa-jar-path "/usr/bin/ditaa"
      org-html-preamble
      (with-temp-buffer (insert-file-contents "preamble.html") (buffer-string))
      org-html-postamble ""
      org-html-head
      "
<link rel=\"stylesheet\" href=\"/main.css\" type=\"text/css\" />
<link rel=\"stylesheet\" href=\"/aoc/style/worg.css\" type=\"text/css\" />
")

(org-babel-do-load-languages
 'org-babel-load-languages
 '((emacs-lisp . t)
   (shell . t)
   (dot . t)
   (clojure . t)
   (org . t)
   (ditaa . t)
   (org . t)
   (plantuml . t)
   (R . t)
   (gnuplot . t)))

(setq org-babel-clojure-backend 'cider)
(require 'cider)

(dolist (org-file (cl-remove-if
		   (lambda (n) (string-match-p "worg/archive/" n))
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
	(if worg-publish-stop-on-error
            (org-html-export-to-html)
          (condition-case err
	      (org-html-export-to-html)
            (error (message (error-message-string err)))))))))
