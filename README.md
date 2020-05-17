# cloud-run-after-request

## Run locally

```
./gradlew run
```

Navigate to `http://localhost:8080`.

## Build

```
gcloud builds submit .
```

## Deploy

```
gcloud run deploy cloud-run-after-request \
  --image asia.gcr.io/orange-sandbox/cloud-run-after-request \
  --region asia-northeast1 \
  --platform managed \
  --allow-unauthenticated \
  --set-env-vars JDBC_URL=sm://orange-sandbox/jdbc-url,QUEUE_PATH=projects/orange-sandbox/locations/asia-northeast1/queues/create-user,TASK_URL=https://capybala.com/ \
  --set-cloudsql-instances orange-sandbox:asia-northeast1:demo \
  --memory 512Mi
```
