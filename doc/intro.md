# Introduction to pepemi

Get
~~~~
curl localhost:3000/api/wish
~~~~

Post
~~~~
curl -X POST --header 'Content-Type: application/edn' http://localhost:3000/api/wish -d '{:name "build" :target "?"}'
~~~~
