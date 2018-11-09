# SpringBoot S3 File Uploading


This app consists in upload files to localstack S3 AWS simulated environment.

### What is Localstack?

LocalStack is a tool that provides an environment for developing an application based on AWS . It provides on your local machine the same functionality and APIs as you can found in AWS cloud environment.

You can find more information here: https://localstack.cloud/

### Setup

To run this application you will need setup your environment with:

  - Docker: https://do.co/2QwjOtN
  - Java 8 or newer
  - AWS CLI Local: https://bit.ly/2PKoijy
 
After all of items are up and running then you will ness to download or clone this repository and configure your AWS Credentials locally following this command:

 - aws configure -> This will ask you some informations like key, secretid and region. You can answer this options with anything you want but you must remember what region you have selected because you will need this to configure the AWS S3 Client.

### How to run

You can run this application after execute the following steps:

 - Step 1: Execute the command "docker-compose up -d" in project root directory.
This will run all items available on localstack and bind the necessary ports of your machine.

 - Step 2: Execute the command "awslocal cloudformation create-stack --stack-name myteststack --template-body file://[full path to project root fol.der]/flat-file-reader/s3-buckets.yaml".
 This command will generate three S3 buckets in your Localstack and give an output like this: 
"{
    "StackId": "arn:aws:cloudformation:us-east-1:123456789:stack/myteststack/27a13f7f-57fc-452b-9d77-b93b28cfb49f"
}". To create my stack, i'm using AWS Cloudformation.

- Step 3: Update the application.yml if needed. This file contains the application configuration, since the bucketname until bucket address.

- Step 4: You can run the application simply executing the command: ./gradlew bootRun or opening this project in your IDE and play.





