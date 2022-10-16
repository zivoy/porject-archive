from discord_webhook import DiscordWebhook

webhook = DiscordWebhook(url="https://discord.com/api/webhooks/908820074943631382/something that should probably be redacted", username="bob", content='Webhook Message')
response = webhook.execute()