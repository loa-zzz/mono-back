# Dockerでの起動手順
## セットアップと実行手順

以下の手順に従って、アプリケーションをローカル環境にセットアップし実行してください。

### 前提条件
以下がインストールされていること

- git
- corretto17jdk
- maven
- docker-desktop

1. **リポジトリをクローンする**
```bash
git clone <リポジトリのURL>
cd <リポジトリ名>
```
2. **Maven資材作成**
```bash
mvn clean package install
```

2. **Dockerビルド、イメージ作成**
```bash
docker compose build --no-cache
docker compose up --force-recreate
```
