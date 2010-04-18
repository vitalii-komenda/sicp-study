
(ns section4-test
  (:use clojure.test
        section4))

(deftest test-self-eval
  (is (= 5 (interpret 5)))
  (is (= "hey" (interpret "hey"))))

(deftest test-expressions
  (are [x y] (= x y)
       3 (interpret '(+ 1 2))
       -14 (interpret '(* (- 10 3) (- 4 6)))
       8 (interpret '(* (/ 4 2) (- 6 4) (+ 1 1)))))

(deftest test-quoted
  (are [x y] (= x y)
       2             (interpret '(quote 2))
       'howdy        (interpret '(quote howdy))
       '(jake jim 2) (interpret '(quote (jake jim 2)))))

(deftest test-if
  (are [x] (true? x)
       (interpret '(if (= 1 1) true false))
       (interpret '(if (= 1 0) false true))))

(deftest test-or
  (is (interpret '(or 5 4 3)))
  (is (false? (interpret '(or false false)))))

(deftest test-and
  (is (true? (interpret '(and true true))))
  (is (false? (interpret '(and false true)))))

(deftest test-vars
  (interpret '(define twelve 12))
  (is (= 12 (interpret 'twelve)))
  (is (= 14 (interpret '(+ twelve 2))))
  (interpret '(define two 2))
  (is (= 2 (interpret 'two)))
  (is (= 14 (interpret '(+ two twelve))))
  (interpret '(set! twelve 9))
  (is (= 9 (interpret 'twelve))))

(deftest test-functions
  (interpret
   '(define (avg a b)
      (/ (+ a b) 2)))

  (is (= 5 (interpret '(avg 4 6))))
  )
