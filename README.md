# Staff Essentials
Minecraft plugin that is restricting countries from where players can join. This plugin also can work as sort of AntiBot/AntiFlood/AntiJoinBot because in that types of attack people are using proxies so they are from all around the world so they wont be able to join because they will be kicked!


## Supported versions

Currently supported versions are:

```bash
✔️ 1-8-1.18.2
```

## How it works?

Plugin takes player ip and stores it in variable and then uses variable to create URL for Ip-api API that they are offering for free which is quite fast for something that is free right? When we make our GET request to their api we get players country in form of JSON object then we get country key from our response that we got from JSON and we compare it to string keys from config.yml that you can edit as much as you want (keep in mind that you are putting allowed countries only) and if player country mathces with country that you didn't restrict player can join easily, but if their country is not matching with any country from config player will be kicked from server!

## Support

Currently I don't think there is any bugs but if you find some feel free to reach out to me through Discord charon#9999

## Contributing
- Fork this repo
- Clone your repo
- Make your changes
- Submit a pull request
