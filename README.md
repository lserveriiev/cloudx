# The demo SpringBoot, AWS(RDS, S3) project

### Settings

#### Configure the AWS S3 settings
Navigate to `src/main/resources/application.yml` and edit the properties:
```yaml
s3:
  endpoint:
    url: http://localstack:4566
  bucket: cloudx
  region: eu-central-1
```
where the `bucket` property is mandatory. If you are using the [localstack](https://github.com/localstack/localstack) 
for the development purpose, you can override the config **endpoint.url**.

### Using the API

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

