Android application creator PRO (by url)
==========

Gradle build script with application

## Getting Started
Build command:
```
./gradlew generateApk -Pconfig=[config.properties]
```

`-Pconfig=` - path to application configuration properties

Example: `./gradlew generateApk -Pconfig=/home/slava/ukrnet.properties] `

Generated apk saved into `generated/` directory with name `[appname].apk`.

#### Application configuration properties
File content:
```
app.name=temp123
app.version=1.1
app.icon=/Volumes/User/icon.png
app.permissions=defaults/permisions.txt

sign.name=appcreator
sign.password=appcreator
sign.years=2000

url=ukr.net
locale=en
actionbar.enabled=true
actionbar.color=#ffffff
orientation=auto
cache=false
browser=internal
zoomcontrol=true

splash.enabled=true
splash.layout=match_parent
splash.background=#ffffff
splash.image=/Volumes/User/123.jpeg

push.enabled=false
push.appid=
push.clientkey=

ad.type=bottom
ad.admobid=ca-app-pub-3940256099942544/6300978111

menu.0.name=Temp0
menu.0.action=refresh
menu.0.icon=ic_at
menu.0.url=google.com.ua

menu.1.name=Temp1
menu.1.action=refresh
menu.1.icon=ic_at
menu.1.url=google.com.ua

menu.2.name=Temp2
menu.2.action=refresh
menu.2.icon=ic_home
menu.2.url=google.com.ua

menu.3.name=Temp3
menu.3.action=url
menu.3.icon=ic_share
menu.3.url=google.com.ua
```
> sign properties are not required (using default instead).
> if no one menu item in config - used default menu.

1. **app.name** - application name
2. **app.version** - application version
2. **app.icon** - path to icon
3. **app.permissions** - path to txt file with permissions
3. **sign.name** - alias name for keystore
6. **sign.password** - password for keystore
7. **sign.year** - validation years
2. **url** - application ulr, e.g. `ukr.net`, `www.google.com`
2. **locale** - application language, available values: `en`, `fr`, `auto`. `auto` - equals device language.
3. **actionbar.enabled** - disable or enable action bar on top, available values: `true`, `false`
4. **actionbar.color** - string representation hex color value, e.g `#ff0000` - red
5. **orientation** - device orientation, available: `portrait`, `landscape`, `auto`
6. **cache** - use cache: `true`, `false`
7. **browser** - browser type: `internal`, `external`
8. **zoomcontrol** - zoom control: `true`, `false`
9. **splash.enabled** - show splash screen: `true`(required background image), `false`
10. **splash.layout** - layout wrap: `wrap_content`, `match_parent`
11. **splash.background** - background hex color of splash screen e.g `#ff0000` - red
12. **splash.image** -path to splash background image, e.g.`/Volumes/User/123.jpeg`
12. **push.enabled** - push notifications: `true`, `false`
13. **push.appid** - gcm app id
14. **push.clientkey** - client key
16. **ad.type** - ad type: `top`, `bottom`, `disabled`
17. **ad.admobid** - ad mob id: string, e.g. `ca-app-pub-3940256099942544/6300978111`
18. **menu.X.name** - name for menu with index X
19. **menu.X.action** - action for menu: `disable`, `refresh`, `share`, `about`, `quit`, `url`
20. **menu.X.icon** - icon for menu: `ic_at`, `ic_home`, `ic_info`, `ic_new`, `ic_close`, `ic_refresh`, `ic_setting`, `ic_share`
21. **menu.X.url** - url for menu action, e.g. `ukr.net`, `www.google.com`


#### Build overview
Gradle loggin params:
`-q` or `--quiet` QUIET and higher
`-i` or `--info` INFO and higher
`-d` or `--debug` DEBUG and higher (that is, all log messages)
Write out of build to file:
`./gradlew generateApk ... >> log.out`