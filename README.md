# EzSyncChat 

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)


A lightweight Minecraft plugin that seamlessly bridges your server chat with Discord using webhooks.

## Features

- **Bidirectional Chat Sync** (Minecraft ↔ Discord)
- **Smart Notifications** for player joins/quits/etc.
- **Custom Branding** with server icons
- **Performance Optimized** - runs async to prevent lag
- **Webhook Management** via in-game commands

## Configuration

Edit `plugins/EzSyncChat/config.yml`:

```yaml
webhook-url: ""  # Your Discord webhook URL
enabled: true    # Toggle plugin on/off
server-icon-url: "" # Custom icon for system messages
```

## Commands

| Command | Description |
|---------|-------------|
| `/ezsyncchat reload` | Refresh config |
| `/ezsyncchat webhook <url>` | Set webhook URL |
| `/ezsyncchat icon <url>` | Set server icon |
| `/ezsyncchat toggle` | Toggle chat sync |

## Technical

**Permissions**:  
`ezsyncchat.admin` - All commands

## Support

Report issues on [GitHub](https://github.com/irgendein-mensch/EzSyncChat/issues)

## License

[MIT](https://choosealicense.com/licenses/mit/) © [@irgendein-mensch](https://github.com/irgendein-mensch)
