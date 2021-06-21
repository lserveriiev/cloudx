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
for the development purpose, you can override the config.

### Using the API

#### Upload a file

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


