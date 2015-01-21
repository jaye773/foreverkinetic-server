[![Build Status](https://travis-ci.org/jaye773/foreverkinetic-server.svg?branch=master)](https://travis-ci.org/jaye773/foreverkinetic-server)

**Note**
On a fresh pull link app to heroku: git remote add heroku git@heroku.com:afternoon-harbor-5993.git

Foreverkinetic-Server
==================

Db Setup
========
Setup your local database with the details as specified in model/model.clj

Simple server to return exercise information.

Use Cases
===========
Show Exercise
Show all exercises with infinite scrolling
Filter exercise list
Search for exercise
Positive exercise vote
Share exercise on social media(facebook, twitter, others)


Server API
===========

/exercise/:id

/exercise_list?limit=3&offest=0&type=”barbell”search_key=”push playground”

/exercise/:id/votes


Misc
===========
We would like the database layer to allow interchangeable database technology. Some how use interface/protocols.

Would like a caching layer. This allows to speedy server responses.

