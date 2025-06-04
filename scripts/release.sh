#!/bin/bash

set -e

IS_HOTFIX=false
if [[ "$1" == "--hotfix" ]]; then
  echo "🚨 Hotfix mode is enabled. Only patch version will be updated."
  IS_HOTFIX=true
fi

if [[ -n $(git status --porcelain) ]]; then
  echo "🚨 You have uncommitted changes locally. Please commit or stash them before trying again."
  exit 1
fi

echo "Starting release pull request preparation... 🚀"

VERSION_TOML_FILE="gradle/libs.versions.toml"

# 現在のversionCodeから1増やす
CURRENT_VERSION_CODE=$(grep '^versionCode' "$VERSION_TOML_FILE" | sed -E 's/.*"([0-9]+)".*/\1/')
NEW_VERSION_CODE=$((CURRENT_VERSION_CODE + 1))

# 現在のversionNameを取得
CURRENT_VERSION_NAME=$(grep '^versionName' "$VERSION_TOML_FILE" | sed -E 's/.*"([0-9]+\.[0-9]+\.[0-9]+)".*/\1/')
IFS='.' read -r MAJOR MINOR PATCH <<< "$CURRENT_VERSION_NAME"

UPDATED_MAJOR_VERSION_NAME="$((MAJOR + 1)).0.0($NEW_VERSION_CODE)"
UPDATED_MINOR_VERSION_NAME="$MAJOR.$((MINOR + 1)).0($NEW_VERSION_CODE)"
UPDATED_PATCH_VERSION_NAME="$MAJOR.$MINOR.$((PATCH + 1))($NEW_VERSION_CODE)"

if $IS_HOTFIX; then
  # hotfix時はパッチバージョンだけを更新
  PATCH=$((PATCH + 1))
else
  # ユーザーにリリースするバージョンを選択させて、MAJOR、MINOR、PATCHを更新
  echo "⚡️ Please select the version to release:"
  select choice in $UPDATED_MAJOR_VERSION_NAME $UPDATED_MINOR_VERSION_NAME $UPDATED_PATCH_VERSION_NAME; do
    case $choice in
      $UPDATED_MAJOR_VERSION_NAME)
        MAJOR=$((MAJOR + 1))
        MINOR=0
        PATCH=0
        break
        ;;
      $UPDATED_MINOR_VERSION_NAME)
        MINOR=$((MINOR + 1))
        PATCH=0
        break
        ;;
      $UPDATED_PATCH_VERSION_NAME)
        PATCH=$((PATCH + 1))
        break
        ;;
      *)
        echo "Please select a number between 1 and 3."
        ;;
    esac
  done
fi

NEW_VERSION_NAME="$MAJOR.$MINOR.$PATCH"
echo "🚀 Updating version to $NEW_VERSION_NAME($NEW_VERSION_CODE)"
read -p "Do you want to release with this version? (y/n): " confirm
if [[ "$confirm" != "y" ]]; then
  echo "🚫 Release preparation has been cancelled."
  exit 1
fi

# ghコマンドがインストールされているか確認
if ! command -v gh >/dev/null 2>&1; then
  echo "🚨 gh command is not installed. Please install GitHub CLI to create a pull request."
  exit 1
fi


if $IS_HOTFIX; then
  # 直近のタグを取得してcheckoutする
  git fetch --tags
  LATEST_TAG=$(git describe --tags --abbrev=0)
  git checkout "$LATEST_TAG"
else
  # developにcheckoutして最新にする
  git checkout develop
  git pull origin develop
fi


# sedコマンドを使ってversionCodeとversionNameを更新
sed -i '' "s/^versionCode = \".*\"/versionCode = \"$NEW_VERSION_CODE\"/" "$VERSION_TOML_FILE"
sed -i '' "s/^versionName = \".*\"/versionName = \"$NEW_VERSION_NAME\"/" "$VERSION_TOML_FILE"

# git commitしてmainにPull Requestを作成
BRANCH_NAME="release/v$NEW_VERSION_NAME"
git checkout -b "$BRANCH_NAME"
git add "$VERSION_TOML_FILE"
git commit -m "chore: update release version $NEW_VERSION_NAME($NEW_VERSION_CODE)"
git push -u origin "$BRANCH_NAME"
gh pr create --base main --head "$BRANCH_NAME" --title "release: update version: v$NEW_VERSION_NAME" --body-file ".github/RELEASE_PULL_REQUEST_TEMPLATE.md"

echo "✅️ Release preparation completed successfully! Please check the pull request on GitHub."
