# The demo SpringBoot, AWS project

### Settings

#### Configure the AWS endpoint
```yaml
aws:
  endpoint:
    url: http://localstack:4566
```
If you are using the [localstack](https://github.com/localstack/localstack)
for the development purpose, you can override the config **endpoint.url**.

#### Configure the AWS S3 settings
Navigate to `src/main/resources/application.yml` and edit the properties:
```yaml
aws:
  s3:
    bucket: cloudx
    region: eu-central-1
```
where the `bucket` property is mandatory.

#### Configure the AWS SNS settings
Navigate to `src/main/resources/application.yml` and edit the properties:
```yaml
aws:
  sns:
    topic-arn: arn:aws:sns:us-east-1:000000000000:CloudX-SNS-uploads-notification-topic
```
where the `sns.topic-arn` has to be created.

#### Configure the Lambda function
```yaml
aws:
  lambda:
    function-name: CloudX-uploads-batch-notifier
```

### Lambda
#### Trigger a lambda function
```shell
 curl --location --request POST 'http://{host}/lambda/trigger' \
--header 'Content-Type: application/json' \
--data-raw '{
    "Records": [
      {"body": "test message"}
    ]
}'
```

### SNS/SQS

#### Create a subscription
```shell
 curl --location --request POST 'http://{host}/sns/subscribe' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "{email}"
}'
```
The email has to be confirmed. Just navigate to the email box {email} and confirm the {email}

Check the subscriptions:
```shell
aws sns list-subscriptions
```
#### Remove a subscription
```shell
curl --location --request DELETE 'http://{host}/sns/unsubscribe' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "{email}"
}'
```

Check the subscriptions:
```shell
aws sns list-subscriptions
```
The response should be an empty list


### RDS

#### 1. Upload a file

```shell
 curl -i -X POST -H "Content-Type: multipart/form-data" -F "file=@{file}" http://{host}/assets/upload
```

**Check the result:**
```sql
SELECT * FROM asset;
```
```shell
aws s3api list-objects --bucket {bucket_name}
```
#### 2. Gather the list of files

```shell
 curl http://{host}/assets | jq
```

#### 3. Gather a file

```shell
 curl http://{host}/assets/{key} | jq
```
where **key** is a s3 key

#### 4. Download a file

```shell
 curl http://{host}/assets/{key}/download
```

#### 5. Delete a file

```shell
 curl --location --request DELETE http://{host}/assets/{key}
```

### How to run the application

#### Compile
```shell
 gradle clean build -x test
```
#### Run
```shell
 java -jar build/libs/aws-0.0.1-SNAPSHOT.jar
```

