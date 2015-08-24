class Event(object):
    def create(self, request):
        body = request.body
        for event in body:
            if event['type'] == 'HttpRequest':
                continue

            print event
