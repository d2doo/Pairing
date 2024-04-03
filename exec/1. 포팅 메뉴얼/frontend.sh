sudo su
cd /home/ubuntu/deploy
rm -rf ./FE/*
tar -xvf febuild.tar -C ./FE/
sudo systemctl restart nginx
