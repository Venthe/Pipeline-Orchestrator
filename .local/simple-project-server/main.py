import http.server
import json
import os
from urllib.parse import unquote
import socketserver

PORT = 4545
DIRECTORY = "projects"


class JSONDirectoryHandler(http.server.SimpleHTTPRequestHandler):
    def list_directory(self, path):
        try:
            directory_listing = []
            if self.path != '/':
                directory_listing.append({"path": "..", "type": "directory"})
            for entry in os.listdir(path):
                full_path = os.path.join(path, entry)
                entry_type = "file"
                if os.path.isdir(full_path):
                    entry_type = "directory"
                directory_listing.append({"path": entry, "type": entry_type})
            response = json.dumps(directory_listing)
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            self.wfile.write(response.encode())
        except OSError:
            self.send_error(404, "Directory not found")

    def do_GET(self):
        self.path = unquote(self.path)
        if os.path.isdir(os.path.join(DIRECTORY, self.path.strip('/'))):
            self.list_directory(os.path.join(DIRECTORY, self.path.strip('/')))
        else:
            super().do_GET()

    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=DIRECTORY, **kwargs)


with socketserver.TCPServer(("", PORT), JSONDirectoryHandler) as httpd:
    print("serving at port", PORT)
    httpd.serve_forever()
