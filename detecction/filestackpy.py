from filestack import Client
client = Client("AY9VEV5GRdSOkw8IWhupGz")

new_filelink = client.upload(filepath='Demo 6.avi', multipart=False)
print('filestack url: ' + new_filelink.url)
