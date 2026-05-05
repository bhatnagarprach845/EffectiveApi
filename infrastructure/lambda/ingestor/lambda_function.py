import json
import urllib3
import os

http = urllib3.PoolManager()

def lambda_handler(event, context):
    # Your Spring Boot API URL (e.g., from AWS App Runner)
    api_url = os.environ['API_URL'] + "/api/v1/monitor/ingest"
    
    for record in event['Records']:
        # This example assumes data is coming from a Kinesis stream or S3 event
        # You would parse your specific log format here
        log_payload = {
            "path": "/api/v1/products", # Extracted from log
            "method": "GET",            # Extracted from log
            "latency": 150              # Extracted from log
        }
        
        # Send to your Spring Boot Backend
        encoded_data = json.dumps(log_payload).encode('utf-8')
        resp = http.request('POST', api_url, 
                            body=encoded_data, 
                            headers={'Content-Type': 'application/json'})
        
    return {'statusCode': 200, 'body': 'Logs Processed'}