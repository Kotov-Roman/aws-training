#!/bin/bash
sudo su -
yum install -y httpd
systemctl start httpd
systemctl enable httpd
cd  /var/www/html/ || exit
mkdir project
aws s3 cp s3://roman-kotov-task1-bucket project --recursive

#this doesn't work for some reasons