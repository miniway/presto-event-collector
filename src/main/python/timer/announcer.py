import uuid
import urlparse
import httplib
import socket
from twest import config

try:
    import simplejson as json
except ImportError:
    import json

service_uuid = '%s' % uuid.uuid1()
conf = config.get_config('collector')

this_port = conf.get('_service', 'port')
this_uri = 'http://%s:%s' % (socket.gethostname(), this_port)
node_id = conf.get('collector', 'node.id')
node_environment = conf.get('collector', 'node.environment', "production")
discovery_uri = conf.get('collector', 'discovery.uri')


def on_timer():
    host, port = urlparse.urlparse(discovery_uri)[1].split(':')
    con = httplib.HTTPConnection(host, int(port), timeout=120)
    headers = {
        'content-type': 'application/json',
        'user-agent': node_id
    }

    data = {
        'environment': node_environment,
        'node_id': node_id,
        'location': '/%s' % node_id,
        'pool': "general",
        'services': [
            {
                'id': service_uuid,
                'type': "collector",
                'properties': {'http': this_uri, 'http-external': this_uri}
            }
        ]
    }

    body = json.dumps(data)

    url = '/v1/announcement/%s' % node_id
    try:
        con.request('PUT', url, body, headers)
        response = con.getresponse()
    except Exception as e:
        print e
        return

    if response.status >= 400:
        print response.status, url, response.read()
    else:
        print response.status, url, body
