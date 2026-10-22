# OfflineTelegram 📡🔒

**Fully offline, end-to-end encrypted messenger** that works without internet, cellular data, or any central server.

Inspired by Telegram, but completely peer-to-peer over **Wi-Fi Direct + Bluetooth**.

## Features (Roadmap)

- 🎤 **Push-to-Talk Walkie-Talkie** + high-quality voice messages
- 💬 Text messages (private chats, groups, channels)
- 📷 Photo & any file sharing (with progress)
- 👥 Groups & public Channels (with public IDs like `@channelname`)
- 🔐 **End-to-End Encryption** (AES-256-GCM + post-quantum hybrid)
- 🔑 Extremely strong key derivation (Argon2id + high entropy random)
- ♿ **Full accessibility** for blind users (TalkBack first-class, high contrast, large touch targets, complete semantic labels)
- 📻 Optional vintage radio-style UI themes (low-fi audio filters)
- 🌐 Works even when phones are on different Wi-Fi networks (via Wi-Fi Direct)
- 🔁 Mesh-style discovery & multi-hop when possible

## Encryption Philosophy

- **Symmetric cipher**: AES-256-GCM (hardware accelerated on virtually all modern Android devices)
- **Key exchange**: X25519 (Curve25519) + planned hybrid with post-quantum ML-KEM (Kyber)
- **Key derivation**: Argon2id with deliberately high memory and time cost so brute-force is impractical
- Perfect forward secrecy where the offline topology allows it
- No server = zero metadata sent to any third party

> Current quantum computers cannot break AES-256 or properly implemented X25519. We design the system so that even future large-scale quantum machines will find it extremely expensive to attack.

## Accessibility First

This app is built **for blind users from day one**:

- Every button, list item, and status has meaningful contentDescription
- Logical focus order
- High-contrast themes by default
- Scalable text (no hard-coded sizes that break TalkBack)
- Announcements for connection status, encryption state, and message delivery

## Build System

This project builds **exclusively** via GitHub Actions.

No local Android Studio or the broken `android-apk-builder` skill is required.

APKs are published as workflow artifacts and (later) GitHub Releases.

## Status

🚧 Early scaffolding. Core P2P + encryption + accessibility foundation coming next.

---

Made for privacy, offline freedom, and maximum fun.
