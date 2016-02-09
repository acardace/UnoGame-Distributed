#!/usr/bin/python

import sys, json, optparse
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer


class RegisterCounter:
	counter = 0
	
	def __init__(self):
    		self.counter = 0

	def getId(self):
		idPlay = 0
		if self.counter < 3:
			idPlay = self.counter
			self.counter = self.counter + 1
		else:
			idPlay = -1

		return idPlay 

# HTTP Request handler class, handling only GET type requests
class Handler(BaseHTTPRequestHandler):
	n = 0
	testId = 0
	counter = RegisterCounter()

	def do_GET(self):
		idPlay = -1

		if self.path == "/register":
			idPlay = self.counter.getId()	

		self.send_response(200)
		self.send_header("Content-type", "application/json")
		self.end_headers()
		self.wfile.write(json.dumps({"idPlay": idPlay}))

		return



##### MAIN ######


address = "127.0.0.1"
port = 8000
maxPlayers = 3

parser = optparse.OptionParser()
parser.add_option('-a', '--address', dest="address", default="x")
parser.add_option('-p', '--port', dest="port", default="x")
parser.add_option('-n', '--max-player', dest="maxPlayer", default="x")
options, remainder = parser.parse_args()
	
if options.address != "x":
	address = options.address
if options.port != "x":
	port = int(options.port)
if options.maxPlayer != "x":
	maxPlayers = int(options.maxPlayer)


print("HTTP Server Started")
addressPort = (address, port)
httpServer = HTTPServer(addressPort, Handler)

print("HTTP server Running OK, listening on %s" %port)

try:
	httpServer.serve_forever()
except KeyboardInterrupt:
	print "Exiting..."
	httpServer.socket.close()
	sys.exit(0)



