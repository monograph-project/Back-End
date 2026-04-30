# notification-service

Complete project snapshot for the `notification-service` folder.

## Project Files

- `notification-service\V1 initial notification schema.sql`
- `notification-service\mvnw.cmd`
- `notification-service\mvnw`
- `notification-service\pom.xml`
- `notification-service\src\test\java\com\final_project\notification_service\NotificationServiceApplicationTests.java`
- `notification-service\src\main\resources\application.yaml`
- `notification-service\src\main\resources\templates\email\article-notification.html`
- `notification-service\src\main\resources\templates\email\password-changed.html`
- `notification-service\src\main\resources\templates\email\comment-replied.html`
- `notification-service\src\main\resources\templates\email\blog-new-comment.html`
- `notification-service\src\main\resources\templates\email\repo-invitation.html`
- `notification-service\src\main\resources\templates\email\password-reset.html`
- `notification-service\src\main\resources\templates\email\repository-notification.html`
- `notification-service\src\main\resources\templates\email\user-registered.html`
- `notification-service\src\main\resources\templates\email\system-invitation.html`
- `notification-service\src\main\java\com\final_project\notification_service\websocket\WebSocketEvents.java`
- `notification-service\src\main\java\com\final_project\notification_service\websocket\WebSocketEventListener.java`
- `notification-service\src\main\java\com\final_project\notification_service\websocket\StompPrincipal.java`
- `notification-service\src\main\java\com\final_project\notification_service\websocket\AuthHandshakeInterceptor.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\UserRegisteredEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\RepositoryOperationEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\PasswordChangedEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\InvitationSentEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\CommentRepliedEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\BlogInteractionEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\event\BlogCommentedEvent.java`
- `notification-service\src\main\java\com\final_project\notification_service\exception\RateLimitExceededException.java`
- `notification-service\src\main\java\com\final_project\notification_service\exception\NotificationNotFoundException.java`
- `notification-service\src\main\java\com\final_project\notification_service\exception\GlobalExceptionHandler.java`
- `notification-service\src\main\java\com\final_project\notification_service\exception\DuplicateNotificationException.java`
- `notification-service\src\main\java\com\final_project\notification_service\exception\BaseException.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\WebSocketNotificationService.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\EmailService.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\response\PagedResponse.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\response\NotificationStatsResponse.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\response\NotificationResponse.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\response\ApiResponse.java`
- `notification-service\src\main\java\com\final_project\notification_service\repo\NotificationRepository.java`
- `notification-service\src\main\java\com\final_project\notification_service\NotificationServiceApplication.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\UserRegisteredConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\RepositoryOperationConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\PasswordChangedConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\InvitationSentConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\CommentRepliedConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\BlogInteractionConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\BlogCommentedConsumer.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\BlogInteractionProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\BlogCommentedProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\RateLimitService.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\NotificationServiceImpl.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\NotificationService.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\IdempotencyService.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\NotificationProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\InvitationSentProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\CommentRepliedProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\RepositoryOperationProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\PasswordChangedProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\service\strategy\UserRegisteredProcessor.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\request\SendNotificationRequest.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\request\ResendNotificationRequest.java`
- `notification-service\src\main\java\com\final_project\notification_service\dto\mapper\NotificationMapper.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\NotificationStatus.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\NotificationChannel.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\Notification.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\ArticleEventType.java`
- `notification-service\src\main\java\com\final_project\notification_service\controller\NotificationController.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\NotificationType.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\RepositoryEventType.java`
- `notification-service\src\main\java\com\final_project\notification_service\model\RepositoryMemberRecipient.java`
- `notification-service\src\main\java\com\final_project\notification_service\config\WebSocketConfig.java`
- `notification-service\src\main\java\com\final_project\notification_service\config\OpenApiConfig.java`
- `notification-service\src\main\java\com\final_project\notification_service\config\KafkaConfig.java`
- `notification-service\src\main\java\com\final_project\notification_service\config\EmailConfig.java`
- `notification-service\src\main\java\com\final_project\notification_service\config\AsyncConfig.java`
- `notification-service\src\main\java\com\final_project\notification_service\config\AppProperties.java`

---

## `notification-service\V1 initial notification schema.sql`

```
-- V1__Initial_notification_schema.sql

CREATE SCHEMA IF NOT EXISTS public;

-- Notifications table
CREATE TABLE IF NOT EXISTS notifications (
                                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Recipient
    recipient_user_id VARCHAR(100) NOT NULL,
    recipient_email VARCHAR(320) NOT NULL,
    recipient_name VARCHAR(200),

    -- Classification
    type VARCHAR(50) NOT NULL CHECK (type IN (
                                     'USER_REGISTERED', 'PASSWORD_CHANGED', 'EMAIL_VERIFIED', 'ACCOUNT_LOCKED',
                                     'SYSTEM_INVITATION', 'REPOSITORY_INVITATION',
                                     'BLOG_NEW_COMMENT', 'BLOG_COMMENT_REPLY', 'BLOG_POST_PUBLISHED',
                                     'CUSTOM'
                                             )),
    channel VARCHAR(20) NOT NULL CHECK (channel IN ('EMAIL', 'SMS', 'PUSH', 'IN_APP')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'PROCESSING', 'SENT', 'FAILED', 'RETRYING', 'SKIPPED')),

    -- Content
    subject VARCHAR(500) NOT NULL,
    body TEXT NOT NULL,

    -- Reference context
    reference_id VARCHAR(100),
    reference_type VARCHAR(50),

    -- Reliability
    idempotency_key VARCHAR(200) NOT NULL UNIQUE,
    retry_count INTEGER DEFAULT 0,
    max_retries INTEGER DEFAULT 3,
    failure_reason TEXT,
    sent_at TIMESTAMP,

    -- Metadata
    metadata TEXT,

    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT check_retry_count CHECK (retry_count >= 0 AND retry_count <= max_retries)
    );

-- Indexes for efficient querying
CREATE INDEX idx_notif_user_id ON notifications(recipient_user_id);
CREATE INDEX idx_notif_type ON notifications(type);
CREATE INDEX idx_notif_status ON notifications(status);
CREATE INDEX idx_notif_created ON notifications(created_at);
CREATE INDEX idx_notif_idempotency ON notifications(idempotency_key);
CREATE INDEX idx_notif_reference ON notifications(reference_id, reference_type);

-- For range queries on status + creation date (failed notifications retry job)
CREATE INDEX idx_notif_status_created ON notifications(status, created_at DESC);

-- For cleanup queries (old sent notifications)
CREATE INDEX idx_notif_sent_at ON notifications(sent_at DESC) WHERE status = 'SENT';
```

---

## `notification-service\mvnw.cmd`

```
<# : batch portion
@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.3.4
@REM
@REM Optional ENV vars
@REM   MVNW_REPOURL - repo url base for downloading maven distribution
@REM   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven
@REM   MVNW_VERBOSE - true: enable verbose log; others: silence the output
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0'; $script='%__MVNW_ARG0_NAME__%'; icm -ScriptBlock ([Scriptblock]::Create((Get-Content -Raw '%~f0'))) -NoNewScope}"`) DO @(
  IF "%%A"=="MVN_CMD" (set __MVNW_CMD__=%%B) ELSE IF "%%B"=="" (echo %%A) ELSE (echo %%A=%%B)
)
@SET PSModulePath=%__MVNW_PSMODULEP_SAVE%
@SET __MVNW_PSMODULEP_SAVE=
@SET __MVNW_ARG0_NAME__=
@SET MVNW_USERNAME=
@SET MVNW_PASSWORD=
@IF NOT "%__MVNW_CMD__%"=="" ("%__MVNW_CMD__%" %*)
@echo Cannot start maven from wrapper >&2 && exit /b 1
@GOTO :EOF
: end batch / begin powershell #>

$ErrorActionPreference = "Stop"
if ($env:MVNW_VERBOSE -eq "true") {
  $VerbosePreference = "Continue"
}

# calculate distributionUrl, requires .mvn/wrapper/maven-wrapper.properties
$distributionUrl = (Get-Content -Raw "$scriptDir/.mvn/wrapper/maven-wrapper.properties" | ConvertFrom-StringData).distributionUrl
if (!$distributionUrl) {
  Write-Error "cannot read distributionUrl property in $scriptDir/.mvn/wrapper/maven-wrapper.properties"
}

switch -wildcard -casesensitive ( $($distributionUrl -replace '^.*/','') ) {
  "maven-mvnd-*" {
    $USE_MVND = $true
    $distributionUrl = $distributionUrl -replace '-bin\.[^.]*$',"-windows-amd64.zip"
    $MVN_CMD = "mvnd.cmd"
    break
  }
  default {
    $USE_MVND = $false
    $MVN_CMD = $script -replace '^mvnw','mvn'
    break
  }
}

# apply MVNW_REPOURL and calculate MAVEN_HOME
# maven home pattern: ~/.m2/wrapper/dists/{apache-maven-<version>,maven-mvnd-<version>-<platform>}/<hash>
if ($env:MVNW_REPOURL) {
  $MVNW_REPO_PATTERN = if ($USE_MVND -eq $False) { "/org/apache/maven/" } else { "/maven/mvnd/" }
  $distributionUrl = "$env:MVNW_REPOURL$MVNW_REPO_PATTERN$($distributionUrl -replace "^.*$MVNW_REPO_PATTERN",'')"
}
$distributionUrlName = $distributionUrl -replace '^.*/',''
$distributionUrlNameMain = $distributionUrlName -replace '\.[^.]*$','' -replace '-bin$',''

$MAVEN_M2_PATH = "$HOME/.m2"
if ($env:MAVEN_USER_HOME) {
  $MAVEN_M2_PATH = "$env:MAVEN_USER_HOME"
}

if (-not (Test-Path -Path $MAVEN_M2_PATH)) {
    New-Item -Path $MAVEN_M2_PATH -ItemType Directory | Out-Null
}

$MAVEN_WRAPPER_DISTS = $null
if ((Get-Item $MAVEN_M2_PATH).Target[0] -eq $null) {
  $MAVEN_WRAPPER_DISTS = "$MAVEN_M2_PATH/wrapper/dists"
} else {
  $MAVEN_WRAPPER_DISTS = (Get-Item $MAVEN_M2_PATH).Target[0] + "/wrapper/dists"
}

$MAVEN_HOME_PARENT = "$MAVEN_WRAPPER_DISTS/$distributionUrlNameMain"
$MAVEN_HOME_NAME = ([System.Security.Cryptography.SHA256]::Create().ComputeHash([byte[]][char[]]$distributionUrl) | ForEach-Object {$_.ToString("x2")}) -join ''
$MAVEN_HOME = "$MAVEN_HOME_PARENT/$MAVEN_HOME_NAME"

if (Test-Path -Path "$MAVEN_HOME" -PathType Container) {
  Write-Verbose "found existing MAVEN_HOME at $MAVEN_HOME"
  Write-Output "MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD"
  exit $?
}

if (! $distributionUrlNameMain -or ($distributionUrlName -eq $distributionUrlNameMain)) {
  Write-Error "distributionUrl is not valid, must end with *-bin.zip, but found $distributionUrl"
}

# prepare tmp dir
$TMP_DOWNLOAD_DIR_HOLDER = New-TemporaryFile
$TMP_DOWNLOAD_DIR = New-Item -Itemtype Directory -Path "$TMP_DOWNLOAD_DIR_HOLDER.dir"
$TMP_DOWNLOAD_DIR_HOLDER.Delete() | Out-Null
trap {
  if ($TMP_DOWNLOAD_DIR.Exists) {
    try { Remove-Item $TMP_DOWNLOAD_DIR -Recurse -Force | Out-Null }
    catch { Write-Warning "Cannot remove $TMP_DOWNLOAD_DIR" }
  }
}

New-Item -Itemtype Directory -Path "$MAVEN_HOME_PARENT" -Force | Out-Null

# Download and Install Apache Maven
Write-Verbose "Couldn't find MAVEN_HOME, downloading and installing it ..."
Write-Verbose "Downloading from: $distributionUrl"
Write-Verbose "Downloading to: $TMP_DOWNLOAD_DIR/$distributionUrlName"

$webclient = New-Object System.Net.WebClient
if ($env:MVNW_USERNAME -and $env:MVNW_PASSWORD) {
  $webclient.Credentials = New-Object System.Net.NetworkCredential($env:MVNW_USERNAME, $env:MVNW_PASSWORD)
}
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$webclient.DownloadFile($distributionUrl, "$TMP_DOWNLOAD_DIR/$distributionUrlName") | Out-Null

# If specified, validate the SHA-256 sum of the Maven distribution zip file
$distributionSha256Sum = (Get-Content -Raw "$scriptDir/.mvn/wrapper/maven-wrapper.properties" | ConvertFrom-StringData).distributionSha256Sum
if ($distributionSha256Sum) {
  if ($USE_MVND) {
    Write-Error "Checksum validation is not supported for maven-mvnd. `nPlease disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties."
  }
  Import-Module $PSHOME\Modules\Microsoft.PowerShell.Utility -Function Get-FileHash
  if ((Get-FileHash "$TMP_DOWNLOAD_DIR/$distributionUrlName" -Algorithm SHA256).Hash.ToLower() -ne $distributionSha256Sum) {
    Write-Error "Error: Failed to validate Maven distribution SHA-256, your Maven distribution might be compromised. If you updated your Maven version, you need to update the specified distributionSha256Sum property."
  }
}

# unzip and move
Expand-Archive "$TMP_DOWNLOAD_DIR/$distributionUrlName" -DestinationPath "$TMP_DOWNLOAD_DIR" | Out-Null

# Find the actual extracted directory name (handles snapshots where filename != directory name)
$actualDistributionDir = ""

# First try the expected directory name (for regular distributions)
$expectedPath = Join-Path "$TMP_DOWNLOAD_DIR" "$distributionUrlNameMain"
$expectedMvnPath = Join-Path "$expectedPath" "bin/$MVN_CMD"
if ((Test-Path -Path $expectedPath -PathType Container) -and (Test-Path -Path $expectedMvnPath -PathType Leaf)) {
  $actualDistributionDir = $distributionUrlNameMain
}

# If not found, search for any directory with the Maven executable (for snapshots)
if (!$actualDistributionDir) {
  Get-ChildItem -Path "$TMP_DOWNLOAD_DIR" -Directory | ForEach-Object {
    $testPath = Join-Path $_.FullName "bin/$MVN_CMD"
    if (Test-Path -Path $testPath -PathType Leaf) {
      $actualDistributionDir = $_.Name
    }
  }
}

if (!$actualDistributionDir) {
  Write-Error "Could not find Maven distribution directory in extracted archive"
}

Write-Verbose "Found extracted Maven distribution directory: $actualDistributionDir"
Rename-Item -Path "$TMP_DOWNLOAD_DIR/$actualDistributionDir" -NewName $MAVEN_HOME_NAME | Out-Null
try {
  Move-Item -Path "$TMP_DOWNLOAD_DIR/$MAVEN_HOME_NAME" -Destination $MAVEN_HOME_PARENT | Out-Null
} catch {
  if (! (Test-Path -Path "$MAVEN_HOME" -PathType Container)) {
    Write-Error "fail to move MAVEN_HOME"
  }
} finally {
  try { Remove-Item $TMP_DOWNLOAD_DIR -Recurse -Force | Out-Null }
  catch { Write-Warning "Cannot remove $TMP_DOWNLOAD_DIR" }
}

Write-Output "MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD"

```

---

## `notification-service\mvnw`

```
#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup batch script, version 3.3.4
#
# Optional ENV vars
# -----------------
#   JAVA_HOME - location of a JDK home dir, required when download maven via java source
#   MVNW_REPOURL - repo url base for downloading maven distribution
#   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven
#   MVNW_VERBOSE - true: enable verbose log; debug: trace the mvnw script; others: silence the output
# ----------------------------------------------------------------------------

set -euf
[ "${MVNW_VERBOSE-}" != debug ] || set -x

# OS specific support.
native_path() { printf %s\\n "$1"; }
case "$(uname)" in
CYGWIN* | MINGW*)
  [ -z "${JAVA_HOME-}" ] || JAVA_HOME="$(cygpath --unix "$JAVA_HOME")"
  native_path() { cygpath --path --windows "$1"; }
  ;;
esac

# set JAVACMD and JAVACCMD
set_java_home() {
  # For Cygwin and MinGW, ensure paths are in Unix format before anything is touched
  if [ -n "${JAVA_HOME-}" ]; then
    if [ -x "$JAVA_HOME/jre/sh/java" ]; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
      JAVACCMD="$JAVA_HOME/jre/sh/javac"
    else
      JAVACMD="$JAVA_HOME/bin/java"
      JAVACCMD="$JAVA_HOME/bin/javac"

      if [ ! -x "$JAVACMD" ] || [ ! -x "$JAVACCMD" ]; then
        echo "The JAVA_HOME environment variable is not defined correctly, so mvnw cannot run." >&2
        echo "JAVA_HOME is set to \"$JAVA_HOME\", but \"\$JAVA_HOME/bin/java\" or \"\$JAVA_HOME/bin/javac\" does not exist." >&2
        return 1
      fi
    fi
  else
    JAVACMD="$(
      'set' +e
      'unset' -f command 2>/dev/null
      'command' -v java
    )" || :
    JAVACCMD="$(
      'set' +e
      'unset' -f command 2>/dev/null
      'command' -v javac
    )" || :

    if [ ! -x "${JAVACMD-}" ] || [ ! -x "${JAVACCMD-}" ]; then
      echo "The java/javac command does not exist in PATH nor is JAVA_HOME set, so mvnw cannot run." >&2
      return 1
    fi
  fi
}

# hash string like Java String::hashCode
hash_string() {
  str="${1:-}" h=0
  while [ -n "$str" ]; do
    char="${str%"${str#?}"}"
    h=$(((h * 31 + $(LC_CTYPE=C printf %d "'$char")) % 4294967296))
    str="${str#?}"
  done
  printf %x\\n $h
}

verbose() { :; }
[ "${MVNW_VERBOSE-}" != true ] || verbose() { printf %s\\n "${1-}"; }

die() {
  printf %s\\n "$1" >&2
  exit 1
}

trim() {
  # MWRAPPER-139:
  #   Trims trailing and leading whitespace, carriage returns, tabs, and linefeeds.
  #   Needed for removing poorly interpreted newline sequences when running in more
  #   exotic environments such as mingw bash on Windows.
  printf "%s" "${1}" | tr -d '[:space:]'
}

scriptDir="$(dirname "$0")"
scriptName="$(basename "$0")"

# parse distributionUrl and optional distributionSha256Sum, requires .mvn/wrapper/maven-wrapper.properties
while IFS="=" read -r key value; do
  case "${key-}" in
  distributionUrl) distributionUrl=$(trim "${value-}") ;;
  distributionSha256Sum) distributionSha256Sum=$(trim "${value-}") ;;
  esac
done <"$scriptDir/.mvn/wrapper/maven-wrapper.properties"
[ -n "${distributionUrl-}" ] || die "cannot read distributionUrl property in $scriptDir/.mvn/wrapper/maven-wrapper.properties"

case "${distributionUrl##*/}" in
maven-mvnd-*bin.*)
  MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/
  case "${PROCESSOR_ARCHITECTURE-}${PROCESSOR_ARCHITEW6432-}:$(uname -a)" in
  *AMD64:CYGWIN* | *AMD64:MINGW*) distributionPlatform=windows-amd64 ;;
  :Darwin*x86_64) distributionPlatform=darwin-amd64 ;;
  :Darwin*arm64) distributionPlatform=darwin-aarch64 ;;
  :Linux*x86_64*) distributionPlatform=linux-amd64 ;;
  *)
    echo "Cannot detect native platform for mvnd on $(uname)-$(uname -m), use pure java version" >&2
    distributionPlatform=linux-amd64
    ;;
  esac
  distributionUrl="${distributionUrl%-bin.*}-$distributionPlatform.zip"
  ;;
maven-mvnd-*) MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/ ;;
*) MVN_CMD="mvn${scriptName#mvnw}" _MVNW_REPO_PATTERN=/org/apache/maven/ ;;
esac

# apply MVNW_REPOURL and calculate MAVEN_HOME
# maven home pattern: ~/.m2/wrapper/dists/{apache-maven-<version>,maven-mvnd-<version>-<platform>}/<hash>
[ -z "${MVNW_REPOURL-}" ] || distributionUrl="$MVNW_REPOURL$_MVNW_REPO_PATTERN${distributionUrl#*"$_MVNW_REPO_PATTERN"}"
distributionUrlName="${distributionUrl##*/}"
distributionUrlNameMain="${distributionUrlName%.*}"
distributionUrlNameMain="${distributionUrlNameMain%-bin}"
MAVEN_USER_HOME="${MAVEN_USER_HOME:-${HOME}/.m2}"
MAVEN_HOME="${MAVEN_USER_HOME}/wrapper/dists/${distributionUrlNameMain-}/$(hash_string "$distributionUrl")"

exec_maven() {
  unset MVNW_VERBOSE MVNW_USERNAME MVNW_PASSWORD MVNW_REPOURL || :
  exec "$MAVEN_HOME/bin/$MVN_CMD" "$@" || die "cannot exec $MAVEN_HOME/bin/$MVN_CMD"
}

if [ -d "$MAVEN_HOME" ]; then
  verbose "found existing MAVEN_HOME at $MAVEN_HOME"
  exec_maven "$@"
fi

case "${distributionUrl-}" in
*?-bin.zip | *?maven-mvnd-?*-?*.zip) ;;
*) die "distributionUrl is not valid, must match *-bin.zip or maven-mvnd-*.zip, but found '${distributionUrl-}'" ;;
esac

# prepare tmp dir
if TMP_DOWNLOAD_DIR="$(mktemp -d)" && [ -d "$TMP_DOWNLOAD_DIR" ]; then
  clean() { rm -rf -- "$TMP_DOWNLOAD_DIR"; }
  trap clean HUP INT TERM EXIT
else
  die "cannot create temp dir"
fi

mkdir -p -- "${MAVEN_HOME%/*}"

# Download and Install Apache Maven
verbose "Couldn't find MAVEN_HOME, downloading and installing it ..."
verbose "Downloading from: $distributionUrl"
verbose "Downloading to: $TMP_DOWNLOAD_DIR/$distributionUrlName"

# select .zip or .tar.gz
if ! command -v unzip >/dev/null; then
  distributionUrl="${distributionUrl%.zip}.tar.gz"
  distributionUrlName="${distributionUrl##*/}"
fi

# verbose opt
__MVNW_QUIET_WGET=--quiet __MVNW_QUIET_CURL=--silent __MVNW_QUIET_UNZIP=-q __MVNW_QUIET_TAR=''
[ "${MVNW_VERBOSE-}" != true ] || __MVNW_QUIET_WGET='' __MVNW_QUIET_CURL='' __MVNW_QUIET_UNZIP='' __MVNW_QUIET_TAR=v

# normalize http auth
case "${MVNW_PASSWORD:+has-password}" in
'') MVNW_USERNAME='' MVNW_PASSWORD='' ;;
has-password) [ -n "${MVNW_USERNAME-}" ] || MVNW_USERNAME='' MVNW_PASSWORD='' ;;
esac

if [ -z "${MVNW_USERNAME-}" ] && command -v wget >/dev/null; then
  verbose "Found wget ... using wget"
  wget ${__MVNW_QUIET_WGET:+"$__MVNW_QUIET_WGET"} "$distributionUrl" -O "$TMP_DOWNLOAD_DIR/$distributionUrlName" || die "wget: Failed to fetch $distributionUrl"
elif [ -z "${MVNW_USERNAME-}" ] && command -v curl >/dev/null; then
  verbose "Found curl ... using curl"
  curl ${__MVNW_QUIET_CURL:+"$__MVNW_QUIET_CURL"} -f -L -o "$TMP_DOWNLOAD_DIR/$distributionUrlName" "$distributionUrl" || die "curl: Failed to fetch $distributionUrl"
elif set_java_home; then
  verbose "Falling back to use Java to download"
  javaSource="$TMP_DOWNLOAD_DIR/Downloader.java"
  targetZip="$TMP_DOWNLOAD_DIR/$distributionUrlName"
  cat >"$javaSource" <<-END
	public class Downloader extends java.net.Authenticator
	{
	  protected java.net.PasswordAuthentication getPasswordAuthentication()
	  {
	    return new java.net.PasswordAuthentication( System.getenv( "MVNW_USERNAME" ), System.getenv( "MVNW_PASSWORD" ).toCharArray() );
	  }
	  public static void main( String[] args ) throws Exception
	  {
	    setDefault( new Downloader() );
	    java.nio.file.Files.copy( java.net.URI.create( args[0] ).toURL().openStream(), java.nio.file.Paths.get( args[1] ).toAbsolutePath().normalize() );
	  }
	}
	END
  # For Cygwin/MinGW, switch paths to Windows format before running javac and java
  verbose " - Compiling Downloader.java ..."
  "$(native_path "$JAVACCMD")" "$(native_path "$javaSource")" || die "Failed to compile Downloader.java"
  verbose " - Running Downloader.java ..."
  "$(native_path "$JAVACMD")" -cp "$(native_path "$TMP_DOWNLOAD_DIR")" Downloader "$distributionUrl" "$(native_path "$targetZip")"
fi

# If specified, validate the SHA-256 sum of the Maven distribution zip file
if [ -n "${distributionSha256Sum-}" ]; then
  distributionSha256Result=false
  if [ "$MVN_CMD" = mvnd.sh ]; then
    echo "Checksum validation is not supported for maven-mvnd." >&2
    echo "Please disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties." >&2
    exit 1
  elif command -v sha256sum >/dev/null; then
    if echo "$distributionSha256Sum  $TMP_DOWNLOAD_DIR/$distributionUrlName" | sha256sum -c - >/dev/null 2>&1; then
      distributionSha256Result=true
    fi
  elif command -v shasum >/dev/null; then
    if echo "$distributionSha256Sum  $TMP_DOWNLOAD_DIR/$distributionUrlName" | shasum -a 256 -c >/dev/null 2>&1; then
      distributionSha256Result=true
    fi
  else
    echo "Checksum validation was requested but neither 'sha256sum' or 'shasum' are available." >&2
    echo "Please install either command, or disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties." >&2
    exit 1
  fi
  if [ $distributionSha256Result = false ]; then
    echo "Error: Failed to validate Maven distribution SHA-256, your Maven distribution might be compromised." >&2
    echo "If you updated your Maven version, you need to update the specified distributionSha256Sum property." >&2
    exit 1
  fi
fi

# unzip and move
if command -v unzip >/dev/null; then
  unzip ${__MVNW_QUIET_UNZIP:+"$__MVNW_QUIET_UNZIP"} "$TMP_DOWNLOAD_DIR/$distributionUrlName" -d "$TMP_DOWNLOAD_DIR" || die "failed to unzip"
else
  tar xzf${__MVNW_QUIET_TAR:+"$__MVNW_QUIET_TAR"} "$TMP_DOWNLOAD_DIR/$distributionUrlName" -C "$TMP_DOWNLOAD_DIR" || die "failed to untar"
fi

# Find the actual extracted directory name (handles snapshots where filename != directory name)
actualDistributionDir=""

# First try the expected directory name (for regular distributions)
if [ -d "$TMP_DOWNLOAD_DIR/$distributionUrlNameMain" ]; then
  if [ -f "$TMP_DOWNLOAD_DIR/$distributionUrlNameMain/bin/$MVN_CMD" ]; then
    actualDistributionDir="$distributionUrlNameMain"
  fi
fi

# If not found, search for any directory with the Maven executable (for snapshots)
if [ -z "$actualDistributionDir" ]; then
  # enable globbing to iterate over items
  set +f
  for dir in "$TMP_DOWNLOAD_DIR"/*; do
    if [ -d "$dir" ]; then
      if [ -f "$dir/bin/$MVN_CMD" ]; then
        actualDistributionDir="$(basename "$dir")"
        break
      fi
    fi
  done
  set -f
fi

if [ -z "$actualDistributionDir" ]; then
  verbose "Contents of $TMP_DOWNLOAD_DIR:"
  verbose "$(ls -la "$TMP_DOWNLOAD_DIR")"
  die "Could not find Maven distribution directory in extracted archive"
fi

verbose "Found extracted Maven distribution directory: $actualDistributionDir"
printf %s\\n "$distributionUrl" >"$TMP_DOWNLOAD_DIR/$actualDistributionDir/mvnw.url"
mv -- "$TMP_DOWNLOAD_DIR/$actualDistributionDir" "$MAVEN_HOME" || [ -d "$MAVEN_HOME" ] || die "fail to move MAVEN_HOME"

clean || :
exec_maven "$@"

```

---

## `notification-service\pom.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.5.13</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.final_project</groupId>
	<artifactId>notification-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name/>
	<description/>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
		<mapstruct.version>1.5.5.Final</mapstruct.version>
		<springdoc.version>2.5.0</springdoc.version>
		<version>2025.0.0</version>
	</properties>
	<dependencies>

		<!-- ── Core ──────────────────────────────────────────────── -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<!-- ── Persistence ───────────────────────────────────────── -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.flywaydb</groupId>
			<artifactId>flyway-core</artifactId>
		</dependency>
		<dependency>
			<groupId>org.flywaydb</groupId>
			<artifactId>flyway-database-postgresql</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bus-amqp</artifactId>
		</dependency>
		<!-- ── Kafka ─────────────────────────────────────────────── -->
		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka</artifactId>
		</dependency>

		<!-- ── Email ─────────────────────────────────────────────── -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>

		<!-- ── Redis (idempotency + rate-limit) ──────────────────── -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>

		<!-- ── Resilience4j ──────────────────────────────────────── -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>

		<!-- ── Observability ─────────────────────────────────────── -->
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-tracing-bridge-otel</artifactId>
		</dependency>
		<dependency>
			<groupId>io.opentelemetry</groupId>
			<artifactId>opentelemetry-exporter-otlp</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.8.16</version>
		</dependency>
		<!-- ── MapStruct ─────────────────────────────────────────── -->
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct</artifactId>
			<version>${mapstruct.version}</version>
		</dependency>
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct-processor</artifactId>
			<version>${mapstruct.version}</version>
			<scope>provided</scope>
		</dependency>
		<!-- ── Jackson DateTime Support ───────────────────────────── -->
		<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-jsr310</artifactId>
		</dependency>

		<!-- ── Lombok ────────────────────────────────────────────── -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>

		<!-- ── Test ──────────────────────────────────────────────── -->

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-websocket</artifactId>
		</dependency>


	</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>2025.0.0</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<executions>
					<execution>
						<id>default-compile</id>
						<phase>compile</phase>
						<goals>
							<goal>compile</goal>
						</goals>
						<configuration>
							<annotationProcessorPaths>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</execution>
					<execution>
						<id>default-testCompile</id>
						<phase>test-compile</phase>
						<goals>
							<goal>testCompile</goal>
						</goals>
						<configuration>
							<annotationProcessorPaths>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</execution>
				</executions>
				<configuration>
					<source>17</source>
					<target>17</target>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>

```

---

## `notification-service\src\test\java\com\final_project\notification_service\NotificationServiceApplicationTests.java`

```
package com.final_project.notification_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}

```

---

## `notification-service\src\main\resources\application.yaml`

```
spring:
  application:
    name: notification-service

  config:
    import: optional:configserver:http://localhost:8888
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true



```

---

## `notification-service\src\main\resources\templates\email\article-notification.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:text="${emailTitle}">Article Notification</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            background: #f5f7fa;
            font-family: Arial, Helvetica, sans-serif;
            color: #1a1a1a;
        }

        .container {
            max-width: 620px;
            margin: 24px auto;
            background: #ffffff;
            border-radius: 14px;
            overflow: hidden;
            box-shadow: 0 6px 18px rgba(10, 61, 98, 0.10);
        }

        .header {
            background: linear-gradient(135deg, #0a3d62, #1a5f7a);
            padding: 36px 28px;
            text-align: center;
        }

        .header-icon {
            width: 54px;
            height: 54px;
            margin: 0 auto 14px;
            border-radius: 50%;
            background: rgba(232, 180, 77, 0.15);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .header-icon svg {
            width: 28px;
            height: 28px;
            stroke: #e8b44d;
        }

        .header h1 {
            margin: 0;
            color: #e8b44d;
            font-size: 28px;
            font-weight: 700;
        }

        .header p {
            margin: 8px 0 0;
            color: #ffffff;
            font-size: 14px;
        }

        .content {
            padding: 32px 28px;
        }

        .greeting {
            font-size: 15px;
            color: #666666;
            margin-bottom: 20px;
        }

        .strong {
            color: #0a3d62;
            font-weight: 700;
        }

        .card {
            border: 1px solid #e4e8ee;
            border-left: 5px solid #e8b44d;
            border-radius: 12px;
            padding: 20px;
            margin: 22px 0;
            background: #f9fbfd;
        }

        .label {
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: #666666;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .title {
            font-size: 18px;
            color: #0a3d62;
            font-weight: 700;
            line-height: 1.4;
            margin: 0;
        }

        .actor-box {
            display: flex;
            gap: 14px;
            align-items: center;
            background: #f5f7fa;
            border: 1px solid #e4e8ee;
            border-radius: 12px;
            padding: 16px;
            margin: 22px 0;
        }

        .avatar {
            min-width: 46px;
            width: 46px;
            height: 46px;
            background: #0a3d62;
            color: #e8b44d;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 18px;
        }

        .actor-name {
            color: #0a3d62;
            font-weight: 700;
            font-size: 15px;
        }

        .actor-email {
            color: #666666;
            font-size: 13px;
            margin-top: 2px;
        }

        .message-box {
            background: #ffffff;
            border: 1px solid #e4e8ee;
            border-radius: 12px;
            padding: 18px;
            margin: 22px 0;
        }

        .message-title {
            color: #0a3d62;
            font-weight: 700;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .message-text {
            color: #666666;
            font-size: 14px;
            line-height: 1.7;
        }

        .details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
            margin: 22px 0;
        }

        .detail-item {
            background: #f5f7fa;
            border: 1px solid #e4e8ee;
            border-radius: 10px;
            padding: 14px;
        }

        .detail-label {
            color: #777777;
            font-size: 11px;
            text-transform: uppercase;
            font-weight: 700;
            margin-bottom: 4px;
        }

        .detail-value {
            color: #0a3d62;
            font-size: 14px;
            font-weight: 700;
        }

        .cta {
            text-align: center;
            margin: 30px 0 18px;
        }

        .cta a {
            background: #0a3d62;
            color: #ffffff;
            text-decoration: none;
            padding: 14px 34px;
            border-radius: 8px;
            display: inline-block;
            font-weight: 700;
            font-size: 14px;
        }

        .note {
            font-size: 13px;
            color: #666666;
            background: rgba(232, 180, 77, 0.10);
            border: 1px solid rgba(232, 180, 77, 0.35);
            border-radius: 10px;
            padding: 14px;
            margin-top: 22px;
        }

        .footer {
            background: #f5f7fa;
            border-top: 1px solid #e4e8ee;
            padding: 24px 28px;
            text-align: center;
            color: #666666;
            font-size: 13px;
            line-height: 1.7;
        }

        .footer strong {
            color: #0a3d62;
        }

        .social {
            margin-top: 14px;
        }

        .social a {
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            margin: 0 6px;
            font-size: 13px;
        }

        @media (max-width: 640px) {
            .container {
                margin: 0;
                border-radius: 0;
            }

            .header {
                padding: 30px 20px;
            }

            .header h1 {
                font-size: 24px;
            }

            .content {
                padding: 24px 20px;
            }

            .details {
                grid-template-columns: 1fr;
            }

            .actor-box {
                align-items: flex-start;
            }

            .cta a {
                display: block;
                padding: 14px 18px;
            }
        }
    </style>
</head>

<body>
<div class="container">

    <div class="header">
        <div class="header-icon">
            <svg th:if="${eventType == 'COMMENT_CREATED'}" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <path d="M21 15a4 4 0 0 1-4 4H7l-4 4V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4z"/>
            </svg>

            <svg th:if="${eventType == 'COMMENT_REPLIED'}" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <path d="M9 17l-5-5 5-5"/>
                <path d="M20 18v-2a4 4 0 0 0-4-4H4"/>
            </svg>

            <svg th:if="${eventType == 'ARTICLE_LIKED'}" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <path d="M20.8 4.6a5.5 5.5 0 0 0-7.8 0L12 5.6l-1-1a5.5 5.5 0 0 0-7.8 7.8l1 1L12 21l7.8-7.6 1-1a5.5 5.5 0 0 0 0-7.8z"/>
            </svg>

            <svg th:if="${eventType == 'ARTICLE_SHARED'}" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <circle cx="18" cy="5" r="3"/>
                <circle cx="6" cy="12" r="3"/>
                <circle cx="18" cy="19" r="3"/>
                <path d="M8.6 13.5l6.8 4"/>
                <path d="M15.4 6.5l-6.8 4"/>
            </svg>

            <svg th:if="${eventType == 'ARTICLE_PUBLISHED'}" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <path d="M20 6L9 17l-5-5"/>
            </svg>
        </div>

        <h1 th:text="${headerTitle}">Article Update</h1>
        <p th:text="${headerSubtitle}">There is a new activity on your article.</p>
    </div>

    <div class="content">
        <p class="greeting">
            Hello <span class="strong" th:text="${recipientName}">User</span>,
        </p>

        <p class="greeting" th:text="${mainMessage}">
            There is a new update related to your article.
        </p>

        <div class="card">
            <div class="label">Article</div>
            <p class="title" th:text="${postTitle}">Article title</p>
        </div>

        <div class="actor-box" th:if="${actorName != null}">
            <div class="avatar" th:text="${actorName.substring(0,1).toUpperCase()}">A</div>
            <div>
                <div class="actor-name" th:text="${actorName}">Actor Name</div>
                <div class="actor-email" th:text="${actorEmail}">actor@example.com</div>
            </div>
        </div>

        <div class="message-box" th:if="${commentSnippet != null}">
            <div class="message-title" th:text="${messageBoxTitle}">Message</div>
            <div class="message-text" th:text="${commentSnippet}">
                Comment or reply content.
            </div>
        </div>

        <div class="details">
            <div class="detail-item" th:if="${sharePlatform != null}">
                <div class="detail-label">Shared on</div>
                <div class="detail-value" th:text="${sharePlatform}">LinkedIn</div>
            </div>

            <div class="detail-item" th:if="${adminName != null}">
                <div class="detail-label">Approved by</div>
                <div class="detail-value" th:text="${adminName}">Admin</div>
            </div>

            <div class="detail-item" th:if="${occurredAt != null}">
                <div class="detail-label">Date</div>
                <div class="detail-value" th:text="${occurredAt}">Today</div>
            </div>

            <div class="detail-item">
                <div class="detail-label">Faculty</div>
                <div class="detail-value">Economics</div>
            </div>
        </div>

        <div class="cta">
            <a th:href="${postUrl}">Open Article</a>
        </div>

        <div class="note">
            You are receiving this notification because this article is connected to your account.
        </div>
    </div>

    <div class="footer">
        <strong>Kandahar University — Faculty of Economics</strong><br>
        Kandahar, Afghanistan<br>
        Email: economics@kandahar-university.edu<br>

        <div class="social">
            <a href="#">Facebook</a>
            <a href="#">LinkedIn</a>
            <a href="#">Website</a>
        </div>
    </div>

</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\password-changed.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Security Alert - Password Change Notification</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            line-height: 1.6;
            color: #1a1a1a;
        }

        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.08);
        }

        /* Header Section */
        .email-header {
            background: linear-gradient(135deg, #d32f2f 0%, #c62828 100%);
            padding: 40px 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .email-header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 300px;
            height: 300px;
            background: rgba(255, 255, 255, 0.08);
            border-radius: 50%;
        }

        .email-header::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 250px;
            height: 250px;
            background: rgba(255, 255, 255, 0.06);
            border-radius: 50%;
        }

        .header-icon {
            position: relative;
            z-index: 1;
            font-size: 48px;
            margin-bottom: 16px;
        }

        .header-title {
            position: relative;
            z-index: 1;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            font-weight: 700;
            color: #ffffff;
            letter-spacing: -0.5px;
            margin-bottom: 8px;
        }

        .header-subtitle {
            position: relative;
            z-index: 1;
            font-size: 13px;
            font-weight: 600;
            color: rgba(255, 255, 255, 0.95);
            letter-spacing: 1px;
            text-transform: uppercase;
        }

        /* Content Section */
        .email-content {
            padding: 40px 30px;
        }

        .greeting-text {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
            margin-bottom: 24px;
        }

        .user-name {
            font-weight: 700;
            color: #d32f2f;
        }

        /* Alert Box */
        .alert-box {
            background: linear-gradient(135deg, rgba(211, 47, 47, 0.08) 0%, rgba(198, 40, 40, 0.05) 100%);
            border-left: 4px solid #d32f2f;
            border-radius: 8px;
            padding: 20px;
            margin: 24px 0;
        }

        .alert-icon {
            display: inline-block;
            margin-right: 12px;
            font-size: 24px;
        }

        .alert-title {
            font-weight: 700;
            color: #d32f2f;
            font-size: 16px;
            margin-bottom: 8px;
        }

        .alert-message {
            color: #666666;
            font-size: 14px;
            line-height: 1.6;
            margin: 0;
        }

        /* Change Details Section */
        .details-section {
            margin: 32px 0;
        }

        .section-title {
            font-family: 'Playfair Display', serif;
            font-size: 20px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 16px;
            padding-bottom: 12px;
            border-bottom: 2px solid #d32f2f;
        }

        .details-box {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #e0e0e0;
        }

        .detail-row {
            display: grid;
            grid-template-columns: 140px 1fr;
            gap: 16px;
            padding: 14px 0;
            border-bottom: 1px solid #e0e0e0;
            align-items: center;
        }

        .detail-row:last-child {
            border-bottom: none;
        }

        .detail-label {
            font-weight: 600;
            color: #666666;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .detail-value {
            color: #0a3d62;
            font-weight: 600;
            font-size: 14px;
            word-break: break-word;
        }

        .detail-icon {
            display: inline-block;
            width: 20px;
            height: 20px;
            margin-right: 8px;
            vertical-align: middle;
        }

        /* Status Badge */
        .status-badge {
            display: inline-block;
            background: #d32f2f;
            color: #ffffff;
            padding: 6px 12px;
            border-radius: 24px;
            font-size: 12px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        /* Security Recommendations */
        .recommendations-section {
            margin: 28px 0;
        }

        .recommendations-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .recommendation-item {
            display: flex;
            gap: 12px;
            padding: 14px;
            background: #f5f7fa;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
        }

        .recommendation-icon {
            flex-shrink: 0;
            width: 32px;
            height: 32px;
            background: linear-gradient(135deg, #d32f2f 0%, #c62828 100%);
            border-radius: 6px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .recommendation-icon svg {
            width: 18px;
            height: 18px;
            color: #ffffff;
        }

        .recommendation-text {
            flex: 1;
        }

        .recommendation-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 13px;
            margin-bottom: 2px;
        }

        .recommendation-description {
            font-size: 11px;
            color: #666666;
            line-height: 1.4;
        }

        /* Action Section */
        .action-section {
            background: linear-gradient(135deg, rgba(211, 47, 47, 0.05) 0%, rgba(198, 40, 40, 0.03) 100%);
            border: 1px solid #d32f2f30;
            border-radius: 12px;
            padding: 24px;
            margin: 28px 0;
            text-align: center;
        }

        .action-title {
            font-weight: 700;
            color: #d32f2f;
            font-size: 15px;
            margin-bottom: 12px;
        }

        .action-text {
            font-size: 14px;
            color: #666666;
            line-height: 1.6;
            margin-bottom: 16px;
        }

        /* CTA Section */
        .cta-section {
            text-align: center;
            margin: 32px 0;
            padding: 32px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #d32f2f 0%, #c62828 100%);
            color: #ffffff;
            padding: 16px 48px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(211, 47, 47, 0.2);
        }

        .cta-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(211, 47, 47, 0.3);
        }

        .cta-text {
            font-size: 13px;
            color: #666666;
            margin-top: 16px;
        }

        /* Security Tips */
        .security-tips {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .security-tips-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 15px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .security-tips-title svg {
            width: 18px;
            height: 18px;
        }

        .security-tips ol {
            margin-left: 20px;
        }

        .security-tips ol li {
            color: #666666;
            font-size: 13px;
            margin-bottom: 8px;
            line-height: 1.5;
        }

        .security-tips strong {
            color: #0a3d62;
        }

        /* Didn't Change */
        .didnt-change-box {
            background: linear-gradient(135deg, rgba(211, 47, 47, 0.1) 0%, rgba(211, 47, 47, 0.05) 100%);
            border-left: 4px solid #d32f2f;
            border-radius: 8px;
            padding: 16px;
            margin: 24px 0;
        }

        .didnt-change-title {
            font-weight: 700;
            color: #d32f2f;
            font-size: 14px;
            margin-bottom: 8px;
        }

        .didnt-change-text {
            font-size: 13px;
            color: #666666;
            line-height: 1.6;
            margin: 0;
        }

        .didnt-change-link {
            color: #d32f2f;
            text-decoration: none;
            font-weight: 600;
        }

        .didnt-change-link:hover {
            text-decoration: underline;
        }

        /* Footer Section */
        .email-footer {
            background-color: #f5f7fa;
            padding: 32px 30px;
            text-align: center;
            border-top: 1px solid #e0e0e0;
        }

        .footer-message {
            font-size: 14px;
            color: #666666;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .footer-divider {
            height: 1px;
            background-color: #e0e0e0;
            margin: 20px 0;
        }

        .footer-contact {
            font-size: 13px;
            color: #666666;
            margin-bottom: 20px;
            line-height: 1.8;
        }

        .footer-contact strong {
            color: #0a3d62;
            display: block;
            margin-bottom: 8px;
        }

        .social-links {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin: 20px 0;
        }

        .social-link {
            display: inline-flex;
            width: 36px;
            height: 36px;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 50%;
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .social-link:hover {
            background-color: #d32f2f;
            color: #ffffff;
            border-color: #d32f2f;
        }

        .social-link svg {
            width: 18px;
            height: 18px;
            stroke: currentColor;
            fill: none;
            stroke-width: 2;
        }

        .copyright {
            font-size: 11px;
            color: #999999;
            margin-top: 20px;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .email-container {
                border-radius: 0;
                margin: 0;
            }

            .email-header {
                padding: 30px 20px;
            }

            .email-content {
                padding: 24px 20px;
            }

            .email-footer {
                padding: 24px 20px;
            }

            .header-title {
                font-size: 28px;
            }

            .header-icon {
                font-size: 40px;
            }

            .detail-row {
                grid-template-columns: 1fr;
                gap: 8px;
            }

            .recommendations-grid {
                grid-template-columns: 1fr;
            }

            .section-title {
                font-size: 18px;
            }

            .cta-button {
                padding: 14px 32px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- Header -->
    <div class="email-header">
        <div class="header-icon">🔒</div>
        <div class="header-title">Security Alert</div>
        <div class="header-subtitle">Password Change Notification</div>
    </div>

    <!-- Content -->
    <div class="email-content">
        <p class="greeting-text">
            Hello <span class="user-name" th:text="${firstName}">User</span>,
        </p>

        <!-- Alert Box -->
        <div class="alert-box">
            <div class="alert-title">
                <span class="alert-icon">⚠️</span>
                Your password was recently <span th:text="${changeType == 'RESET' ? 'reset' : 'changed'}">changed</span>
            </div>
            <p class="alert-message">
                This is a security notification to confirm this action on your account. If you did not authorize this change, please take immediate action.
            </p>
        </div>

        <!-- Change Details Section -->
        <div class="details-section">
            <div class="section-title">
                <svg style="display: inline-block; margin-right: 8px; width: 24px; height: 24px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polyline points="12 6 12 12 16 14"></polyline>
                </svg>
                Change Details
            </div>
            <div class="details-box">
                <div class="detail-row">
                    <div class="detail-label">Change Type</div>
                    <div class="detail-value">
                        <span class="status-badge" th:text="${changeType == 'RESET' ? 'Password Reset' : 'Password Changed'}">Change</span>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Timestamp</div>
                    <div class="detail-value" th:text="${#dates.format(occurredAt, 'MMM dd, yyyy HH:mm:ss')}">January 15, 2024 14:30:00</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">IP Address</div>
                    <div class="detail-value" th:text="${ipAddress}">192.168.1.100</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Device/Location</div>
                    <div class="detail-value" th:text="${deviceInfo}">Chrome on Windows</div>
                </div>
            </div>
        </div>

        <!-- Action Section -->
        <div class="action-section">
            <div class="action-title">
                <svg style="display: inline-block; margin-right: 8px; width: 18px; height: 18px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <path d="M12 6v6m0 0v6"></path>
                </svg>
                Is This You?
            </div>
            <p class="action-text">
                If you made this change, no further action is needed. Your account is secure.
            </p>
        </div>

        <!-- Didn't Change Box -->
        <div class="didnt-change-box">
            <div class="didnt-change-title">
                ⚡ Didn't Make This Change?
            </div>
            <p class="didnt-change-text">
                If you didn't authorize this password change, your account may be compromised.
                <a th:href="${securityUrl}" class="didnt-change-link">Click here to secure your account immediately</a> or contact our support team.
            </p>
        </div>

        <!-- Security Recommendations -->
        <div class="recommendations-section">
            <div class="section-title">
                <svg style="display: inline-block; margin-right: 8px; width: 24px; height: 24px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                </svg>
                Keep Your Account Secure
            </div>
            <div class="recommendations-grid">
                <div class="recommendation-item">
                    <div class="recommendation-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                            <path d="M12 8v4m0 4h.01"></path>
                        </svg>
                    </div>
                    <div class="recommendation-text">
                        <div class="recommendation-title">Use Strong Passwords</div>
                        <div class="recommendation-description">Combine uppercase, numbers, and symbols</div>
                    </div>
                </div>

                <div class="recommendation-item">
                    <div class="recommendation-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                            <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                        </svg>
                    </div>
                    <div class="recommendation-text">
                        <div class="recommendation-title">Enable 2FA</div>
                        <div class="recommendation-description">Add extra layer of security</div>
                    </div>
                </div>

                <div class="recommendation-item">
                    <div class="recommendation-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path>
                        </svg>
                    </div>
                    <div class="recommendation-text">
                        <div class="recommendation-title">Review Activity</div>
                        <div class="recommendation-description">Check login history regularly</div>
                    </div>
                </div>

                <div class="recommendation-item">
                    <div class="recommendation-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="1"></circle>
                            <path d="M12 1v6m0 6v6"></path>
                            <path d="M4.22 4.22l4.24 4.24m6.08 0l4.24-4.24"></path>
                            <path d="M1 12h6m6 0h6"></path>
                            <path d="M4.22 19.78l4.24-4.24m6.08 0l4.24 4.24"></path>
                        </svg>
                    </div>
                    <div class="recommendation-text">
                        <div class="recommendation-title">Never Share</div>
                        <div class="recommendation-description">Keep password confidential always</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Security Tips -->
        <div class="security-tips">
            <div class="security-tips-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polyline points="12 6 12 12 16 14"></polyline>
                </svg>
                What to Do Now
            </div>
            <ol>
                <li><strong>Verify</strong> this email is legitimate - we'll never ask for your password</li>
                <li><strong>Review</strong> the change details above to confirm they're correct</li>
                <li><strong>Update</strong> your password regularly (every 3-6 months recommended)</li>
                <li><strong>Enable</strong> two-factor authentication for enhanced security</li>
                <li><strong>Contact support</strong> immediately if something looks wrong</li>
            </ol>
        </div>

        <!-- CTA Section -->
        <div class="cta-section">
            <a th:href="${securityUrl}" class="cta-button">
                <svg style="width: 16px; height: 16px; display: inline; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                    <path d="M9 12l2 2 4-4"></path>
                </svg>
                Review Security Settings
            </a>
            <p class="cta-text">Access your account security center to manage passwords and settings</p>
        </div>

        <!-- Additional Info -->
        <div style="background: #f5f7fa; padding: 20px; border-radius: 8px; margin: 24px 0; border: 1px solid #e0e0e0;">
            <div style="font-weight: 700; color: #0a3d62; font-size: 14px; margin-bottom: 12px;">
                📧 Questions About This Email?
            </div>
            <p style="font-size: 13px; color: #666666; line-height: 1.6; margin: 0;">
                If you believe this email is fraudulent or you have concerns about your account security, please contact our support team immediately. We're here to help protect your information.
            </p>
        </div>
    </div>

    <!-- Footer -->
    <div class="email-footer">
        <div class="footer-message">
            <strong>Your security is our priority.</strong><br>
            This email was sent because a password change was initiated on your account.
        </div>

        <div class="footer-divider"></div>

        <div class="footer-contact">
            <strong>Kandahar University<br>Faculty of Economics</strong>
            Email: <strong>support@kandahar-university.edu</strong><br>
            Phone: <strong>+93 (0) XXX-XXX-XXXX</strong><br>
            Website: <strong>www.kandahar-university.edu</strong>
        </div>

        <div class="social-links">
            <a href="#" class="social-link" title="Facebook">
                <svg viewBox="0 0 24 24">
                    <path d="M18 2h-3a6 6 0 0 0-6 6v3H7v4h2v8h4v-8h3l1-4h-4V8a1 1 0 0 1 1-1h3z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Twitter">
                <svg viewBox="0 0 24 24">
                    <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2s9 5 20 5a9.5 9.5 0 00-9-5.5c4.75 2.25 7-7 7-7"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="LinkedIn">
                <svg viewBox="0 0 24 24">
                    <path d="M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z"></path>
                    <circle cx="4" cy="4" r="2"></circle>
                </svg>
            </a>
        </div>

        <div class="copyright">
            © 2024 Kandahar University - Faculty of Economics. All rights reserved.
        </div>
    </div>
</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\comment-replied.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Reply to Your Comment</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            line-height: 1.6;
            color: #1a1a1a;
        }

        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.08);
        }

        /* Header Section */
        .email-header {
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            padding: 40px 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .email-header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 300px;
            height: 300px;
            background: rgba(232, 180, 77, 0.08);
            border-radius: 50%;
        }

        .email-header::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 250px;
            height: 250px;
            background: rgba(232, 180, 77, 0.06);
            border-radius: 50%;
        }

        .header-icon {
            position: relative;
            z-index: 1;
            font-size: 48px;
            margin-bottom: 16px;
        }

        .header-title {
            position: relative;
            z-index: 1;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            font-weight: 700;
            color: #e8b44d;
            letter-spacing: -0.5px;
            margin-bottom: 8px;
        }

        .header-subtitle {
            position: relative;
            z-index: 1;
            font-size: 13px;
            font-weight: 600;
            color: rgba(255, 255, 255, 0.95);
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        /* Content Section */
        .email-content {
            padding: 40px 30px;
        }

        .greeting-text {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
            margin-bottom: 24px;
        }

        .user-name {
            font-weight: 700;
            color: #0a3d62;
        }

        /* Post Info Card */
        .post-info-card {
            background: linear-gradient(135deg, rgba(26, 95, 122, 0.05) 0%, rgba(232, 180, 77, 0.08) 100%);
            border: 2px solid #e8b44d;
            border-radius: 12px;
            padding: 24px;
            margin: 24px 0;
            position: relative;
        }

        .post-info-card::before {
            content: '';
            position: absolute;
            top: -12px;
            left: 24px;
            width: 24px;
            height: 24px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .post-label {
            font-size: 11px;
            font-weight: 700;
            color: #666666;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
            margin-top: 8px;
        }

        .post-title {
            font-family: 'Playfair Display', serif;
            font-size: 18px;
            font-weight: 700;
            color: #0a3d62;
            line-height: 1.4;
        }

        /* Comment Thread Section */
        .comment-thread {
            margin: 32px 0;
        }

        .section-title {
            font-family: 'Playfair Display', serif;
            font-size: 20px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #e8b44d;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .section-title svg {
            width: 24px;
            height: 24px;
        }

        /* Comment Items */
        .comment-item {
            margin-bottom: 16px;
            border-radius: 8px;
            overflow: hidden;
        }

        .your-comment {
            background: #f5f7fa;
            border-left: 4px solid #0a3d62;
        }

        .reply-comment {
            background: linear-gradient(135deg, rgba(232, 180, 77, 0.08) 0%, rgba(26, 95, 122, 0.05) 100%);
            border-left: 4px solid #e8b44d;
            margin-left: 24px;
        }

        .comment-header {
            padding: 16px 20px;
            background: rgba(0, 0, 0, 0.02);
            border-bottom: 1px solid rgba(0, 0, 0, 0.05);
        }

        .comment-meta {
            font-size: 13px;
            color: #666666;
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 4px;
        }

        .comment-author {
            font-weight: 700;
            color: #0a3d62;
        }

        .comment-badge {
            display: inline-block;
            background: #0a3d62;
            color: #e8b44d;
            padding: 2px 8px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .reply-badge {
            background: #e8b44d;
            color: #0a3d62;
        }

        .comment-time {
            font-size: 11px;
            color: #999999;
        }

        .comment-body {
            padding: 20px;
            color: #666666;
            font-size: 14px;
            line-height: 1.7;
        }

        .comment-highlight {
            background: rgba(232, 180, 77, 0.2);
            padding: 12px 16px;
            border-radius: 4px;
            margin: 12px 0;
            border-left: 3px solid #e8b44d;
            font-style: italic;
            color: #0a3d62;
        }

        /* Reply Indicator */
        .reply-indicator {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 0 20px 8px 20px;
            color: #666666;
            font-size: 12px;
        }

        .reply-indicator svg {
            width: 16px;
            height: 16px;
            color: #e8b44d;
        }

        /* Engagement Info */
        .engagement-section {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .engagement-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .engagement-title svg {
            width: 18px;
            height: 18px;
        }

        .engagement-stats {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .stat-item {
            background: #ffffff;
            padding: 12px;
            border-radius: 6px;
            border: 1px solid #e0e0e0;
        }

        .stat-label {
            font-size: 11px;
            color: #999999;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            font-weight: 600;
            margin-bottom: 4px;
        }

        .stat-value {
            font-size: 18px;
            font-weight: 700;
            color: #0a3d62;
        }

        /* CTA Section */
        .cta-section {
            text-align: center;
            margin: 32px 0;
            padding: 32px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            color: #ffffff;
            padding: 16px 48px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.2);
        }

        .cta-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(10, 61, 98, 0.3);
        }

        .cta-text {
            font-size: 13px;
            color: #666666;
            margin-top: 16px;
        }

        /* Notification Preferences */
        .notification-prefs {
            background: #f5f7fa;
            padding: 16px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .notification-prefs-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 13px;
            margin-bottom: 8px;
        }

        .notification-prefs-text {
            font-size: 12px;
            color: #666666;
            line-height: 1.5;
        }

        .notification-prefs-link {
            color: #0a3d62;
            text-decoration: none;
            font-weight: 600;
        }

        .notification-prefs-link:hover {
            text-decoration: underline;
        }

        /* Footer Section */
        .email-footer {
            background-color: #f5f7fa;
            padding: 32px 30px;
            text-align: center;
            border-top: 1px solid #e0e0e0;
        }

        .footer-message {
            font-size: 14px;
            color: #666666;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .footer-divider {
            height: 1px;
            background-color: #e0e0e0;
            margin: 20px 0;
        }

        .footer-contact {
            font-size: 13px;
            color: #666666;
            margin-bottom: 20px;
            line-height: 1.8;
        }

        .footer-contact strong {
            color: #0a3d62;
            display: block;
            margin-bottom: 8px;
        }

        .social-links {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin: 20px 0;
        }

        .social-link {
            display: inline-flex;
            width: 36px;
            height: 36px;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 50%;
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .social-link:hover {
            background-color: #0a3d62;
            color: #ffffff;
            border-color: #0a3d62;
        }

        .social-link svg {
            width: 18px;
            height: 18px;
            stroke: currentColor;
            fill: none;
            stroke-width: 2;
        }

        .copyright {
            font-size: 11px;
            color: #999999;
            margin-top: 20px;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .email-container {
                border-radius: 0;
                margin: 0;
            }

            .email-header {
                padding: 30px 20px;
            }

            .email-content {
                padding: 24px 20px;
            }

            .email-footer {
                padding: 24px 20px;
            }

            .header-title {
                font-size: 28px;
            }

            .header-icon {
                font-size: 40px;
            }

            .reply-comment {
                margin-left: 0;
                border-left-width: 3px;
            }

            .engagement-stats {
                grid-template-columns: 1fr;
            }

            .section-title {
                font-size: 18px;
            }

            .cta-button {
                padding: 14px 32px;
                font-size: 14px;
            }

            .post-title {
                font-size: 16px;
            }
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- Header -->
    <div class="email-header">
        <div class="header-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 20.8457C16.8325 20.8457 20.75 16.9282 20.75 12.0957C20.75 7.26321 16.8325 3.3457 12 3.3457C7.16751 3.3457 3.25 7.26321 3.25 12.0957C3.25 14.5119 4.22938 16.6994 5.81282 18.2829L3.25 20.8457H12Z" stroke="#323544" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
        </div>
        <div class="header-title">New Reply</div>
        <div class="header-subtitle"> <span class="user-name" th:text="${replierName}"></span> Responded to Your Comment</div>
    </div>

    <!-- Content -->
    <div class="email-content">
        <p class="greeting-text">
            Hello <span class="user-name" th:text="${commenterName}">there</span>,
        </p>

        <p style="font-size: 15px; color: #666666; margin-bottom: 16px;">
            <span class="user-name" th:text="${replierName}"></span> replied to your comment on:
        </p>

        <!-- Post Info Card -->
        <div class="post-info-card">
            <div class="post-label">📝 Article</div>
            <div class="post-title" th:text="${postTitle}">Economic Policies and Their Impact on Development</div>
        </div>

        <!-- Comment Thread -->
        <div class="comment-thread">
            <div class="section-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                </svg>
                Comment Thread
            </div>

            <!-- Your Original Comment -->
            <div class="comment-item your-comment">
                <div class="comment-header">
                    <div class="comment-meta">
                        <span class="comment-author" th:text="${commenterName}">You</span>
                        <span class="comment-badge">Original Comment</span>
                    </div>
                    <div class="comment-time" th:text="${#dates.format(commentDate, 'MMM dd, yyyy HH:mm')}">Jan 15, 2024 14:30</div>
                </div>
                <div class="comment-body" th:text="${originalComment}">
                    This is a really insightful analysis of modern economic trends and policy implementation...
                </div>
            </div>

            <!-- Reply Indicator -->
            <div class="reply-indicator">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="9 10 4 15 9 20"></polyline>
                    <path d="M20 4v7a7 7 0 0 1-7 7H4"></path>
                </svg>
                Reply from <span class="comment-author" th:text="${replierName}">Jane Smith</span>
            </div>

            <!-- Reply Comment -->
            <div class="comment-item reply-comment">
                <div class="comment-header">
                    <div class="comment-meta">
                        <span class="comment-author" th:text="${replierName}">Jane Smith</span>
                        <span class="comment-badge reply-badge">Reply</span>
                    </div>
                    <div class="comment-time" th:text="${#dates.format(replyDate, 'MMM dd, yyyy HH:mm')}">Jan 15, 2024 16:45</div>
                </div>
                <div class="comment-body">
                    <p style="margin-bottom: 12px;">
                        Great point! I completely agree with your analysis. Your observation about market dynamics is particularly insightful.
                    </p>
                    <div class="comment-highlight" th:text="${replySnippet}">
                        I would like to add that the empirical evidence from recent studies supports your hypothesis...
                    </div>
                    <p style="margin-top: 12px;">
                        Let's continue this discussion in more detail!
                    </p>
                </div>
            </div>
        </div>

        <!-- Engagement Stats -->
        <div class="engagement-section">
            <div class="engagement-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="23 6 13.5 15.5 8.5 10.5 1 17"></polyline>
                    <polyline points="17 6 23 6 23 12"></polyline>
                </svg>
                Discussion Engagement
            </div>
            <div class="engagement-stats">
                <div class="stat-item">
                    <div class="stat-label">Total Comments</div>
                    <div class="stat-value" th:text="${totalComments}">12</div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">Total Likes</div>
                    <div class="stat-value" th:text="${totalLikes}">34</div>
                </div>
            </div>
        </div>

        <!-- CTA Section -->
        <div class="cta-section">
            <a th:href="${postUrl}" class="cta-button">
                <svg style="width: 16px; height: 16px; display: inline; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M9 11l3 3L22 4"></path>
                    <path d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                View Full Discussion
            </a>
            <p class="cta-text">Read the reply and continue the conversation</p>
        </div>

        <!-- Notification Preferences -->
        <div class="notification-prefs">
            <div class="notification-prefs-title">📧 Notification Preferences</div>
            <p class="notification-prefs-text">
                You're receiving this because someone replied to your comment.
                <a th:href="${notificationPreferencesUrl}" class="notification-prefs-link">Manage your notification settings</a>
                to control which discussions you follow.
            </p>
        </div>

        <!-- Additional Context -->
        <div style="background: #f5f7fa; padding: 20px; border-radius: 8px; margin: 24px 0; border: 1px solid #e0e0e0;">
            <div style="font-weight: 700; color: #0a3d62; font-size: 14px; margin-bottom: 12px;">
                💡 Why You Received This Email
            </div>
            <p style="font-size: 13px; color: #666666; line-height: 1.6; margin: 0;">
                We send notifications when someone replies to your comments, so you can stay updated on discussions you've participated in. This helps foster active engagement in our academic community.
            </p>
        </div>
    </div>

    <!-- Footer -->
    <div class="email-footer">
        <div class="footer-message">
            <strong>Keep the conversation going!</strong><br>
            Your participation helps enrich our academic community with diverse perspectives.
        </div>

        <div class="footer-divider"></div>

        <div class="footer-contact">
            <strong>Kandahar University<br>Faculty of Economics</strong>
            Email: <strong>blog@kandahar-university.edu</strong><br>
            Phone: <strong>+93 (0) XXX-XXX-XXXX</strong><br>
            Website: <strong>www.kandahar-university.edu</strong>
        </div>

        <div class="social-links">
            <a href="#" class="social-link" title="Facebook">
                <svg viewBox="0 0 24 24">
                    <path d="M18 2h-3a6 6 0 0 0-6 6v3H7v4h2v8h4v-8h3l1-4h-4V8a1 1 0 0 1 1-1h3z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Twitter">
                <svg viewBox="0 0 24 24">
                    <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2s9 5 20 5a9.5 9.5 0 00-9-5.5c4.75 2.25 7-7 7-7"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="LinkedIn">
                <svg viewBox="0 0 24 24">
                    <path d="M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z"></path>
                    <circle cx="4" cy="4" r="2"></circle>
                </svg>
            </a>
        </div>

        <div class="copyright">
            © 2024 Kandahar University - Faculty of Economics. All rights reserved.
        </div>
    </div>
</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\blog-new-comment.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Comment on Your Blog Post</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            line-height: 1.6;
            color: #1a1a1a;
        }

        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.08);
        }

        /* Header Section */
        .email-header {
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            padding: 40px 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .email-header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 300px;
            height: 300px;
            background: rgba(232, 180, 77, 0.08);
            border-radius: 50%;
        }

        .email-header::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 250px;
            height: 250px;
            background: rgba(232, 180, 77, 0.06);
            border-radius: 50%;
        }

        .header-icon {
            position: relative;
            z-index: 1;
            font-size: 48px;
            margin-bottom: 16px;
        }

        .header-title {
            position: relative;
            z-index: 1;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            font-weight: 700;
            color: #e8b44d;
            letter-spacing: -0.5px;
            margin-bottom: 8px;
        }

        .header-subtitle {
            position: relative;
            z-index: 1;
            font-size: 13px;
            font-weight: 600;
            color: rgba(255, 255, 255, 0.95);
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        /* Content Section */
        .email-content {
            padding: 40px 30px;
        }

        .greeting-text {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
            margin-bottom: 24px;
        }

        .author-name {
            font-weight: 700;
            color: #0a3d62;
        }

        /* Post Info Card */
        .post-info-card {
            background: linear-gradient(135deg, rgba(26, 95, 122, 0.05) 0%, rgba(232, 180, 77, 0.08) 100%);
            border: 2px solid #e8b44d;
            border-radius: 12px;
            padding: 24px;
            margin: 24px 0;
            position: relative;
        }

        .post-info-card::before {
            content: '';
            position: absolute;
            top: -12px;
            left: 24px;
            width: 24px;
            height: 24px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 50%;
        }

        .post-label {
            font-size: 11px;
            font-weight: 700;
            color: #666666;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
            margin-top: 8px;
        }

        .post-title {
            font-family: 'Playfair Display', serif;
            font-size: 18px;
            font-weight: 700;
            color: #0a3d62;
            line-height: 1.4;
        }

        /* Commenter Info */
        .commenter-section {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 12px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
            display: grid;
            grid-template-columns: 60px 1fr;
            gap: 16px;
            align-items: center;
        }

        .commenter-avatar {
            width: 60px;
            height: 60px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            font-weight: 700;
            color: #e8b44d;
        }

        .commenter-info h3 {
            font-size: 15px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 4px;
        }

        .commenter-info p {
            font-size: 13px;
            color: #666666;
            margin: 0;
        }

        /* Comment Box */
        .comment-box {
            background: #f5f7fa;
            border: 2px solid #e0e0e0;
            border-radius: 12px;
            padding: 24px;
            margin: 24px 0;
            border-left: 4px solid #e8b44d;
        }

        .comment-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 16px;
            padding-bottom: 12px;
            border-bottom: 1px solid #e0e0e0;
        }

        .comment-author-info {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .comment-author-name {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
        }

        .comment-badge {
            display: inline-block;
            background: #e8b44d;
            color: #0a3d62;
            padding: 4px 10px;
            border-radius: 16px;
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .comment-time {
            font-size: 12px;
            color: #999999;
        }

        .comment-body {
            color: #666666;
            font-size: 14px;
            line-height: 1.7;
        }

        .comment-body p {
            margin-bottom: 12px;
        }

        .comment-body p:last-child {
            margin-bottom: 0;
        }

        /* Comment Stats */
        .comment-stats {
            background: #ffffff;
            padding: 16px;
            border-radius: 8px;
            margin-top: 16px;
            border: 1px solid #e0e0e0;
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 12px;
            text-align: center;
        }

        .stat-item {
            padding: 8px 0;
        }

        .stat-label {
            font-size: 11px;
            color: #999999;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            font-weight: 600;
            margin-bottom: 4px;
        }

        .stat-value {
            font-size: 16px;
            font-weight: 700;
            color: #0a3d62;
        }

        /* Action Section */
        .action-section {
            background: linear-gradient(135deg, rgba(232, 180, 77, 0.08) 0%, rgba(26, 95, 122, 0.05) 100%);
            border: 1px solid #e8b44d30;
            border-radius: 12px;
            padding: 24px;
            margin: 28px 0;
            text-align: center;
        }

        .action-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 15px;
            margin-bottom: 12px;
        }

        .action-text {
            font-size: 14px;
            color: #666666;
            line-height: 1.6;
            margin-bottom: 0;
        }

        /* CTA Section */
        .cta-section {
            text-align: center;
            margin: 32px 0;
            padding: 32px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            color: #ffffff;
            padding: 16px 48px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.2);
        }

        .cta-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(10, 61, 98, 0.3);
        }

        .cta-text {
            font-size: 13px;
            color: #666666;
            margin-top: 16px;
        }

        /* Post Engagement Section */
        .engagement-section {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 12px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .engagement-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .engagement-title svg {
            width: 18px;
            height: 18px;
        }

        .engagement-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .engagement-item {
            background: #ffffff;
            padding: 12px;
            border-radius: 6px;
            border: 1px solid #e0e0e0;
            text-align: center;
        }

        .engagement-label {
            font-size: 11px;
            color: #999999;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            font-weight: 600;
            margin-bottom: 6px;
        }

        .engagement-count {
            font-size: 18px;
            font-weight: 700;
            color: #0a3d62;
        }

        /* Response Suggestions */
        .response-tips {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .response-tips-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .response-tips-title svg {
            width: 18px;
            height: 18px;
        }

        .response-tips ul {
            margin-left: 20px;
        }

        .response-tips ul li {
            color: #666666;
            font-size: 13px;
            margin-bottom: 8px;
            line-height: 1.5;
        }

        .response-tips strong {
            color: #0a3d62;
        }

        /* Notification Preferences */
        .notification-prefs {
            background: #f5f7fa;
            padding: 16px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .notification-prefs-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 13px;
            margin-bottom: 8px;
        }

        .notification-prefs-text {
            font-size: 12px;
            color: #666666;
            line-height: 1.5;
        }

        .notification-prefs-link {
            color: #0a3d62;
            text-decoration: none;
            font-weight: 600;
        }

        .notification-prefs-link:hover {
            text-decoration: underline;
        }

        /* Footer Section */
        .email-footer {
            background-color: #f5f7fa;
            padding: 32px 30px;
            text-align: center;
            border-top: 1px solid #e0e0e0;
        }

        .footer-message {
            font-size: 14px;
            color: #666666;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .footer-divider {
            height: 1px;
            background-color: #e0e0e0;
            margin: 20px 0;
        }

        .footer-contact {
            font-size: 13px;
            color: #666666;
            margin-bottom: 20px;
            line-height: 1.8;
        }

        .footer-contact strong {
            color: #0a3d62;
            display: block;
            margin-bottom: 8px;
        }

        .social-links {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin: 20px 0;
        }

        .social-link {
            display: inline-flex;
            width: 36px;
            height: 36px;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 50%;
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .social-link:hover {
            background-color: #0a3d62;
            color: #ffffff;
            border-color: #0a3d62;
        }

        .social-link svg {
            width: 18px;
            height: 18px;
            stroke: currentColor;
            fill: none;
            stroke-width: 2;
        }

        .copyright {
            font-size: 11px;
            color: #999999;
            margin-top: 20px;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .email-container {
                border-radius: 0;
                margin: 0;
            }

            .email-header {
                padding: 30px 20px;
            }

            .email-content {
                padding: 24px 20px;
            }

            .email-footer {
                padding: 24px 20px;
            }

            .header-title {
                font-size: 28px;
            }

            .header-icon {
                font-size: 40px;
            }

            .commenter-section {
                grid-template-columns: 50px 1fr;
                gap: 12px;
            }

            .commenter-avatar {
                width: 50px;
                height: 50px;
                font-size: 20px;
            }

            .engagement-grid {
                grid-template-columns: 1fr;
            }

            .comment-stats {
                grid-template-columns: 1fr;
            }

            .cta-button {
                padding: 14px 32px;
                font-size: 14px;
            }

            .post-title {
                font-size: 16px;
            }

            .comment-header {
                flex-direction: column;
                align-items: flex-start;
            }

            .comment-time {
                margin-top: 8px;
            }
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- Header -->
    <div class="email-header">
        <div class="header-icon">💬</div>
        <div class="header-title">New Comment</div>
        <div class="header-subtitle"> <span class="author-name" th:text="${authorName}"> </span> Commented on Your Post</div>
    </div>

    <!-- Content -->
    <div class="email-content">
        <p class="greeting-text">
            Hello <span class="author-name" th:text="${authorName}">Author</span>,
        </p>

        <p style="font-size: 15px; color: #666666; margin-bottom: 16px;">
            <span class="author-name" th:text="${commenterName}">John Doe</span> just left a comment on your blog post:
        </p>

        <!-- Post Info Card -->
        <div class="post-info-card">
            <div class="post-label">
                <span>
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
<path d="M15.1043 14.2745C15.5376 14.2745 15.8986 14.443 16.1874 14.78C16.4762 15.0687 16.6206 15.4297 16.6206 15.8629C16.6206 16.2962 16.4761 16.6572 16.1872 16.946C15.8985 17.2348 15.5376 17.3792 15.1043 17.3792H8.82281C8.38947 17.3792 8.02845 17.2347 7.73976 16.9458C7.45086 16.6571 7.30641 16.2962 7.30641 15.8629C7.30641 15.4297 7.45086 15.0687 7.73976 14.7799C8.02855 14.4429 8.38957 14.2745 8.82281 14.2745H15.1043ZM8.82281 9.65363C8.38947 9.65363 8.02845 9.50918 7.73976 9.22028C7.45086 8.93149 7.30641 8.57047 7.30641 8.13723C7.30641 7.70409 7.45086 7.34312 7.73976 7.05432C8.02855 6.76542 8.38957 6.62098 8.82281 6.62098H12.866C13.2993 6.62098 13.6604 6.76542 13.9491 7.05432C14.238 7.34312 14.3824 7.70409 14.3824 8.13723C14.3824 8.57057 14.238 8.93159 13.9491 9.22028C13.6603 9.50918 13.2992 9.65363 12.866 9.65363H8.82281ZM18.1367 9.65363V6.62098C18.1367 5.36939 17.6795 4.28634 16.765 3.3718C15.8504 2.45727 14.7673 2 13.5159 2H7.37864C6.12716 2 5.04415 2.45727 4.12961 3.3718C3.21508 4.28634 2.75781 5.36939 2.75781 6.62098V17.379C2.75781 18.6305 3.21508 19.7135 4.12961 20.628C5.04415 21.5427 6.12716 22 7.37864 22H16.6206C17.8721 22 18.9551 21.5427 19.8696 20.628C20.7842 19.7135 21.2416 18.6305 21.2416 17.379V12.7582C21.2416 12.3249 21.0971 11.9638 20.8082 11.675C20.5194 11.3862 20.1585 11.2418 19.7253 11.2418C19.2921 11.2418 18.907 11.0974 18.57 10.8086C18.2811 10.4717 18.1367 10.0869 18.1367 9.65363Z" fill="#323544"/>
</svg>

                </span>
                Article</div>
            <div class="post-title" th:text="${postTitle}">Economic Policies and Their Impact on Development</div>
        </div>

        <!-- Commenter Info Section -->
        <div class="commenter-section">
            <div class="commenter-avatar" th:text="${commenterName.charAt(0)}">J</div>
            <div class="commenter-info">
                <h3 th:text="${commenterName}">John Doe</h3>
                <p th:text="${commenterEmail}">john.doe@example.com</p>
            </div>
        </div>

        <!-- Comment Box -->
        <div class="comment-box">
            <div class="comment-header">
                <div class="comment-author-info">
                    <span class="comment-author-name" th:text="${commenterName}">John Doe</span>
                    <span class="comment-badge">New Comment</span>
                </div>
                <div class="comment-time" th:text="${#dates.format(commentDate, 'MMM dd, yyyy HH:mm')}">Jan 15, 2024 14:30</div>
            </div>
            <div class="comment-body" th:text="${commentSnippet}">
                This is a great post! I really enjoyed reading about the economic implications and your thoughtful analysis.
                Your perspective on market dynamics is particularly insightful and well-supported by empirical evidence.
            </div>
            <div class="comment-stats">
                <div class="stat-item">
                    <div class="stat-label">
                        <svg width="15" height="15" viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M4.89346 2.35248C3.49195 2.35248 2.35248 3.49359 2.35248 4.90532C2.35248 6.38164 3.20954 7.9168 4.37255 9.33522C5.39396 10.581 6.59464 11.6702 7.50002 12.4778C8.4054 11.6702 9.60608 10.581 10.6275 9.33522C11.7905 7.9168 12.6476 6.38164 12.6476 4.90532C12.6476 3.49359 11.5081 2.35248 10.1066 2.35248C9.27059 2.35248 8.81894 2.64323 8.5397 2.95843C8.27877 3.25295 8.14623 3.58566 8.02501 3.88993C8.00391 3.9429 7.98315 3.99501 7.96211 4.04591C7.88482 4.23294 7.7024 4.35494 7.50002 4.35494C7.29765 4.35494 7.11523 4.23295 7.03793 4.04592C7.01689 3.99501 6.99612 3.94289 6.97502 3.8899C6.8538 3.58564 6.72126 3.25294 6.46034 2.95843C6.18109 2.64323 5.72945 2.35248 4.89346 2.35248ZM1.35248 4.90532C1.35248 2.94498 2.936 1.35248 4.89346 1.35248C6.0084 1.35248 6.73504 1.76049 7.20884 2.2953C7.32062 2.42147 7.41686 2.55382 7.50002 2.68545C7.58318 2.55382 7.67941 2.42147 7.79119 2.2953C8.265 1.76049 8.99164 1.35248 10.1066 1.35248C12.064 1.35248 13.6476 2.94498 13.6476 4.90532C13.6476 6.74041 12.6013 8.50508 11.4008 9.96927C10.2636 11.3562 8.92194 12.5508 8.00601 13.3664C7.94645 13.4194 7.88869 13.4709 7.83291 13.5206C7.64324 13.6899 7.3568 13.6899 7.16713 13.5206C7.11135 13.4709 7.05359 13.4194 6.99403 13.3664C6.0781 12.5508 4.73641 11.3562 3.59926 9.96927C2.39872 8.50508 1.35248 6.74041 1.35248 4.90532Z" fill="currentColor" fill-rule="evenodd" clip-rule="evenodd"></path></svg>
                        Likes</div>
                    <div class="stat-value" th:text="${commentLikes}"></div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">
                        <svg width="15" height="15" viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M7.94721 0.164594C7.66569 0.0238299 7.33431 0.0238302 7.05279 0.164594L0.552786 3.41459C0.214002 3.58399 0 3.93025 0 4.30902V12C0 12.5523 0.447715 13 1 13H14C14.5523 13 15 12.5523 15 12V4.30902C15 3.93025 14.786 3.58399 14.4472 3.41459L7.94721 0.164594ZM13.5689 4.09349L7.5 1.05902L1.43105 4.09349L7.5 7.29136L13.5689 4.09349ZM1 4.88366V12H14V4.88366L7.70977 8.19813C7.57848 8.26731 7.42152 8.26731 7.29023 8.19813L1 4.88366Z" fill="currentColor" fill-rule="evenodd" clip-rule="evenodd"></path></svg>
                        Replies</div>
                    <div class="stat-value" th:text="${commentReplies}"></div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">
                        <svg width="15" height="15" viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M6.97942 1.25171L6.9585 1.30199L5.58662 4.60039C5.54342 4.70426 5.44573 4.77523 5.3336 4.78422L1.7727 5.0697L1.71841 5.07405L1.38687 5.10063L1.08608 5.12475C0.820085 5.14607 0.712228 5.47802 0.914889 5.65162L1.14406 5.84793L1.39666 6.06431L1.43802 6.09974L4.15105 8.42374C4.23648 8.49692 4.2738 8.61176 4.24769 8.72118L3.41882 12.196L3.40618 12.249L3.32901 12.5725L3.25899 12.866C3.19708 13.1256 3.47945 13.3308 3.70718 13.1917L3.9647 13.0344L4.24854 12.861L4.29502 12.8326L7.34365 10.9705C7.43965 10.9119 7.5604 10.9119 7.6564 10.9705L10.705 12.8326L10.7515 12.861L11.0354 13.0344L11.2929 13.1917C11.5206 13.3308 11.803 13.1256 11.7411 12.866L11.671 12.5725L11.5939 12.249L11.5812 12.196L10.7524 8.72118C10.7263 8.61176 10.7636 8.49692 10.849 8.42374L13.562 6.09974L13.6034 6.06431L13.856 5.84793L14.0852 5.65162C14.2878 5.47802 14.18 5.14607 13.914 5.12475L13.6132 5.10063L13.2816 5.07405L13.2274 5.0697L9.66645 4.78422C9.55432 4.77523 9.45663 4.70426 9.41343 4.60039L8.04155 1.30199L8.02064 1.25171L7.89291 0.944609L7.77702 0.665992C7.67454 0.419604 7.32551 0.419604 7.22303 0.665992L7.10715 0.944609L6.97942 1.25171ZM7.50003 2.60397L6.50994 4.98442C6.32273 5.43453 5.89944 5.74207 5.41351 5.78103L2.84361 5.98705L4.8016 7.66428C5.17183 7.98142 5.33351 8.47903 5.2204 8.95321L4.62221 11.461L6.8224 10.1171C7.23842 9.86302 7.76164 9.86302 8.17766 10.1171L10.3778 11.461L9.77965 8.95321C9.66654 8.47903 9.82822 7.98142 10.1984 7.66428L12.1564 5.98705L9.58654 5.78103C9.10061 5.74207 8.67732 5.43453 8.49011 4.98442L7.50003 2.60397Z" fill="currentColor" fill-rule="evenodd" clip-rule="evenodd"></path></svg>
                        Rating</div>
                    <div class="stat-value" th:text="${commentRating}"></div>
                </div>
            </div>
        </div>

        <!-- Action Section -->
        <div class="action-section">
            <div class="action-title">
                <svg style="display: inline-block; width: 18px; height: 18px; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <path d="M12 6v6m0 0v6"></path>
                </svg>
                Engage With Your Audience
            </div>
            <p class="action-text">
                This comment helps build community around your content. Consider responding to keep the conversation going!
            </p>
        </div>

        <!-- Post Engagement Stats -->
        <div class="engagement-section">
            <div class="engagement-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="23 6 13.5 15.5 8.5 10.5 1 17"></polyline>
                    <polyline points="17 6 23 6 23 12"></polyline>
                </svg>
                Post Engagement
            </div>
            <div class="engagement-grid">
                <div class="engagement-item">
                    <div class="engagement-label">Total Comments</div>
                    <div class="engagement-count" th:text="${totalComments}">12</div>
                </div>
                <div class="engagement-item">
                    <div class="engagement-label">Total Views</div>
                    <div class="engagement-count" th:text="${totalViews}">342</div>
                </div>
            </div>
        </div>

        <!-- Response Tips -->
        <div class="response-tips">
            <div class="response-tips-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polyline points="12 6 12 12 16 14"></polyline>
                </svg>
                Tips for Responding
            </div>
            <ul>
                <li><strong>Thank them</strong> for their thoughtful engagement with your content</li>
                <li><strong>Address key points</strong> they raised in their comment</li>
                <li><strong>Add value</strong> with additional insights or resources</li>
                <li><strong>Ask follow-up questions</strong> to deepen the discussion</li>
                <li><strong>Be professional</strong> and maintain academic tone</li>
            </ul>
        </div>

        <!-- CTA Section -->
        <div class="cta-section">
            <a th:href="${postUrl}" class="cta-button">
                <svg style="width: 16px; height: 16px; display: inline; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M9 11l3 3L22 4"></path>
                    <path d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                View & Reply to Comment
            </a>
            <p class="cta-text">Open your blog post to see the comment and craft your response</p>
        </div>

        <!-- Notification Preferences -->
        <div class="notification-prefs">
            <div class="notification-prefs-title">📧 Notification Preferences</div>
            <p class="notification-prefs-text">
                You're receiving this because comments are enabled on your posts.
                <a th:href="${notificationPreferencesUrl}" class="notification-prefs-link">Manage your notification settings</a>
                to control which types of interactions you're notified about.
            </p>
        </div>

        <!-- Additional Context -->
        <div style="background: #f5f7fa; padding: 20px; border-radius: 8px; margin: 24px 0; border: 1px solid #e0e0e0;">
            <div style="font-weight: 700; color: #0a3d62; font-size: 14px; margin-bottom: 12px;">
                 Building Your Blog Community
            </div>
            <p style="font-size: 13px; color: #666666; line-height: 1.6; margin: 0;">
                Every comment represents an engaged reader interested in your work. Responding promptly and thoughtfully strengthens your academic community and encourages more meaningful discussions around your research and insights.
            </p>
        </div>
    </div>

    <!-- Footer -->
    <div class="email-footer">
        <div class="footer-message">
            <strong>Your readers value your voice.</strong><br>
            Keep the conversation alive by responding to comments and building your academic community.
        </div>

        <div class="footer-divider"></div>

        <div class="footer-contact">
            <strong>Kandahar University<br>Faculty of Economics</strong>
            Email: <strong>blog@kandahar-university.edu</strong><br>
            Phone: <strong>+93 (0) XXX-XXX-XXXX</strong><br>
            Website: <strong>www.kandahar-university.edu</strong>
        </div>

        <div class="social-links">
            <a href="#" class="social-link" title="Facebook">
                <svg viewBox="0 0 24 24">
                    <path d="M18 2h-3a6 6 0 0 0-6 6v3H7v4h2v8h4v-8h3l1-4h-4V8a1 1 0 0 1 1-1h3z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Twitter">
                <svg viewBox="0 0 24 24">
                    <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2s9 5 20 5a9.5 9.5 0 00-9-5.5c4.75 2.25 7-7 7-7"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="LinkedIn">
                <svg viewBox="0 0 24 24">
                    <path d="M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z"></path>
                    <circle cx="4" cy="4" r="2"></circle>
                </svg>
            </a>
        </div>

        <div class="copyright">
            © 2024 Kandahar University - Faculty of Economics. All rights reserved.
        </div>
    </div>
</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\repo-invitation.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Repository Collaboration Invitation</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:wght@600;700&family=Fira+Code:wght@400;500;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            line-height: 1.6;
            color: #1a1a1a;
        }

        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.08);
        }

        /* Header Section */
        .email-header {
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            padding: 40px 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .email-header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 300px;
            height: 300px;
            background: rgba(232, 180, 77, 0.08);
            border-radius: 50%;
        }

        .email-header::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 250px;
            height: 250px;
            background: rgba(232, 180, 77, 0.06);
            border-radius: 50%;
        }

        .header-icon {
            position: relative;
            z-index: 1;
            font-size: 48px;
            margin-bottom: 16px;
        }

        .header-title {
            position: relative;
            z-index: 1;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            font-weight: 700;
            color: #e8b44d;
            letter-spacing: -0.5px;
            margin-bottom: 8px;
        }

        .header-subtitle {
            position: relative;
            z-index: 1;
            font-size: 13px;
            font-weight: 600;
            color: rgba(255, 255, 255, 0.95);
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        /* Content Section */
        .email-content {
            padding: 40px 30px;
        }

        .greeting-text {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
            margin-bottom: 24px;
        }

        .invitee-name {
            font-weight: 700;
            color: #0a3d62;
        }

        /* Repository Card */
        .repo-card {
            background: linear-gradient(135deg, rgba(232, 180, 77, 0.08) 0%, rgba(26, 95, 122, 0.05) 100%);
            border: 2px solid #e8b44d;
            border-radius: 12px;
            padding: 28px;
            margin: 28px 0;
            position: relative;
        }

        .repo-card::before {
            content: '';
            position: absolute;
            top: -14px;
            left: 30px;
            width: 28px;
            height: 28px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
        }

        .repo-info {
            margin-top: 12px;
        }

        .repo-name {
            font-family: 'Fira Code', monospace;
            font-size: 16px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 8px;
            word-break: break-all;
        }

        .repo-path {
            font-family: 'Fira Code', monospace;
            font-size: 12px;
            color: #666666;
            background: rgba(0, 0, 0, 0.02);
            padding: 8px 12px;
            border-radius: 4px;
            margin-bottom: 12px;
            border-left: 2px solid #e8b44d;
        }

        .repo-description {
            font-size: 14px;
            line-height: 1.7;
            color: #666666;
        }

        /* Inviter Info */
        .inviter-section {
            background: #f5f7fa;
            padding: 24px;
            border-radius: 12px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
            display: grid;
            grid-template-columns: 60px 1fr;
            gap: 16px;
            align-items: center;
        }

        .inviter-avatar {
            width: 60px;
            height: 60px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            font-weight: 700;
            color: #e8b44d;
        }

        .inviter-info h3 {
            font-size: 15px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 4px;
        }

        .inviter-info p {
            font-size: 13px;
            color: #666666;
            margin-bottom: 4px;
        }

        .inviter-role {
            font-size: 12px;
            color: #999999;
            font-weight: 600;
        }

        /* Repository Details */
        .repo-details {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 12px;
            margin: 24px 0;
        }

        .detail-card {
            background: #f5f7fa;
            padding: 16px;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
            text-align: center;
        }

        .detail-label {
            font-size: 11px;
            color: #666666;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .detail-value {
            font-family: 'Fira Code', monospace;
            font-size: 14px;
            color: #0a3d62;
            font-weight: 700;
        }

        /* Permissions Section */
        .permissions-section {
            margin: 28px 0;
        }

        .section-title {
            font-family: 'Playfair Display', serif;
            font-size: 20px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 16px;
            padding-bottom: 12px;
            border-bottom: 2px solid #e8b44d;
        }

        .permissions-list {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .permission-item {
            display: flex;
            gap: 12px;
            padding: 12px;
            background: #f5f7fa;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
            align-items: flex-start;
        }

        .permission-check {
            flex-shrink: 0;
            width: 20px;
            height: 20px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #e8b44d;
            font-weight: 700;
            font-size: 12px;
            margin-top: 2px;
        }

        .permission-text {
            flex: 1;
        }

        .permission-name {
            font-weight: 700;
            color: #0a3d62;
            font-size: 13px;
            margin-bottom: 2px;
        }

        .permission-description {
            font-size: 12px;
            color: #666666;
            line-height: 1.4;
        }

        /* What You Can Do */
        .features-section {
            margin: 28px 0;
        }

        .features-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .feature-item {
            display: flex;
            gap: 10px;
            padding: 14px;
            background: #f5f7fa;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
        }

        .feature-icon {
            flex-shrink: 0;
            width: 36px;
            height: 36px;
            background: linear-gradient(135deg, #e8b44d 0%, #d4992f 100%);
            border-radius: 6px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .feature-icon svg {
            width: 20px;
            height: 20px;
            color: #ffffff;
        }

        .feature-text {
            flex: 1;
        }

        .feature-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 13px;
            margin-bottom: 2px;
        }

        .feature-description {
            font-size: 11px;
            color: #666666;
            line-height: 1.4;
        }

        /* CTA Section */
        .cta-section {
            text-align: center;
            margin: 32px 0;
            padding: 32px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            color: #ffffff;
            padding: 16px 48px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.2);
        }

        .cta-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(10, 61, 98, 0.3);
        }

        .cta-text {
            font-size: 13px;
            color: #666666;
            margin-top: 16px;
        }

        /* Invitation Link */
        .invitation-link {
            background: #f9f9f9;
            padding: 14px;
            border-left: 4px solid #0a3d62;
            margin: 16px 0;
            font-family: 'Fira Code', monospace;
            color: #0a3d62;
            font-size: 11px;
            word-break: break-all;
            border-radius: 4px;
            font-weight: 600;
            overflow-wrap: break-word;
            line-height: 1.5;
        }

        /* Expires Note */
        .expires-note {
            background: linear-gradient(135deg, rgba(232, 180, 77, 0.1) 0%, rgba(232, 180, 77, 0.05) 100%);
            border-left: 4px solid #e8b44d;
            padding: 14px;
            margin: 16px 0;
            border-radius: 4px;
            font-size: 13px;
            color: #0a3d62;
        }

        .expires-note strong {
            color: #0a3d62;
        }

        .expires-icon {
            display: inline-block;
            margin-right: 8px;
        }

        /* Quick Start Guide */
        .quick-start {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .quick-start-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .quick-start-title svg {
            width: 18px;
            height: 18px;
        }

        .quick-start ol {
            margin-left: 20px;
            color: #666666;
        }

        .quick-start ol li {
            margin-bottom: 8px;
            font-size: 13px;
            line-height: 1.5;
        }

        .quick-start code {
            background: rgba(0, 0, 0, 0.05);
            padding: 2px 6px;
            border-radius: 3px;
            font-family: 'Fira Code', monospace;
            font-size: 11px;
            color: #0a3d62;
        }

        /* Footer Section */
        .email-footer {
            background-color: #f5f7fa;
            padding: 32px 30px;
            text-align: center;
            border-top: 1px solid #e0e0e0;
        }

        .footer-message {
            font-size: 14px;
            color: #666666;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .footer-divider {
            height: 1px;
            background-color: #e0e0e0;
            margin: 20px 0;
        }

        .footer-contact {
            font-size: 13px;
            color: #666666;
            margin-bottom: 20px;
            line-height: 1.8;
        }

        .footer-contact strong {
            color: #0a3d62;
            display: block;
            margin-bottom: 8px;
        }

        .social-links {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin: 20px 0;
        }

        .social-link {
            display: inline-flex;
            width: 36px;
            height: 36px;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 50%;
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .social-link:hover {
            background-color: #0a3d62;
            color: #ffffff;
            border-color: #0a3d62;
        }

        .social-link svg {
            width: 18px;
            height: 18px;
            stroke: currentColor;
            fill: none;
            stroke-width: 2;
        }

        .copyright {
            font-size: 11px;
            color: #999999;
            margin-top: 20px;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .email-container {
                border-radius: 0;
                margin: 0;
            }

            .email-header {
                padding: 30px 20px;
            }

            .email-content {
                padding: 24px 20px;
            }

            .email-footer {
                padding: 24px 20px;
            }

            .header-title {
                font-size: 28px;
            }

            .header-icon {
                font-size: 40px;
            }

            .repo-card {
                padding: 20px;
            }

            .repo-name {
                font-size: 14px;
            }

            .repo-details {
                grid-template-columns: 1fr;
            }

            .permissions-list {
                grid-template-columns: 1fr;
            }

            .features-grid {
                grid-template-columns: 1fr;
            }

            .inviter-section {
                grid-template-columns: 50px 1fr;
                gap: 12px;
            }

            .inviter-avatar {
                width: 50px;
                height: 50px;
                font-size: 20px;
            }

            .section-title {
                font-size: 18px;
            }

            .cta-button {
                padding: 14px 32px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- Header -->
    <div class="email-header">
        <div class="header-icon">🚀</div>
        <div class="header-title">Repository Collaboration</div>
        <div class="header-subtitle">You've Been Invited</div>
    </div>

    <!-- Content -->
    <div class="email-content">
        <p class="greeting-text">
            Hello <span class="invitee-name" th:text="${inviteeName != null ? inviteeName : 'Developer'}">Developer</span>,
        </p>

        <!-- Repository Card -->
        <div class="repo-card">
            <div class="repo-info">
                <div class="repo-name" th:text="${repositoryName}">awesome-economics-project</div>
                <div class="repo-path" th:text="${repositoryPath}">kandahar-university/faculty-economics</div>
                <p class="repo-description" th:text="${repositoryDescription}">
                    A collaborative project for advanced economic analysis and research
                </p>
            </div>
        </div>

        <!-- Inviter Section -->
        <div class="inviter-section">
            <div class="inviter-avatar" th:text="${inviterName.charAt(0)}">A</div>
            <div class="inviter-info">
                <h3 th:text="${inviterName}">Dr. Ahmad Hassan</h3>
                <p th:text="${inviterEmail}">ahmad.hassan@kandahar-university.edu</p>
                <p class="inviter-role" th:text="${inviterRole}">Project Lead</p>
            </div>
        </div>

        <!-- Repository Details -->
        <div class="repo-details">
            <div class="detail-card">
                <div class="detail-label">Visibility</div>
                <div class="detail-value" th:text="${repositoryVisibility}">Private</div>
            </div>
            <div class="detail-card">
                <div class="detail-label">Type</div>
                <div class="detail-value">Repository</div>
            </div>
            <div class="detail-card">
                <div class="detail-label">Your Role</div>
                <div class="detail-value" th:text="${inviteRole}">Collaborator</div>
            </div>
        </div>

        <!-- Permissions Section -->
        <div class="permissions-section">
            <div class="section-title">
                <svg style="display: inline-block; margin-right: 8px; width: 24px; height: 24px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                </svg>
                Your Permissions
            </div>
            <div class="permissions-list">
                <div class="permission-item">
                    <div class="permission-check">✓</div>
                    <div class="permission-text">
                        <div class="permission-name">Read Code</div>
                        <div class="permission-description">Access and view all repository files</div>
                    </div>
                </div>

                <div class="permission-item">
                    <div class="permission-check">✓</div>
                    <div class="permission-text">
                        <div class="permission-name">Create Branches</div>
                        <div class="permission-description">Push code and create pull requests</div>
                    </div>
                </div>

                <div class="permission-item">
                    <div class="permission-check">✓</div>
                    <div class="permission-text">
                        <div class="permission-name">View Issues</div>
                        <div class="permission-description">See project tasks and discussions</div>
                    </div>
                </div>

                <div class="permission-item">
                    <div class="permission-check">✓</div>
                    <div class="permission-text">
                        <div class="permission-name">Participate</div>
                        <div class="permission-description">Comment and collaborate on tasks</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- What You Can Do -->
        <div class="features-section">
            <div class="section-title">What's Next?</div>
            <div class="features-grid">
                <div class="feature-item">
                    <div class="feature-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="1"></circle>
                            <path d="M12 1v6m0 6v6"></path>
                            <path d="M4.22 4.22l4.24 4.24m6.08 0l4.24-4.24"></path>
                            <path d="M1 12h6m6 0h6"></path>
                            <path d="M4.22 19.78l4.24-4.24m6.08 0l4.24 4.24"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Clone Repo</div>
                        <div class="feature-description">Set up the code locally</div>
                    </div>
                </div>

                <div class="feature-item">
                    <div class="feature-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M12 5v14M5 12h14"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Create Branches</div>
                        <div class="feature-description">Start working on features</div>
                    </div>
                </div>

                <div class="feature-item">
                    <div class="feature-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M9 11l3 3L22 4"></path>
                            <path d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Submit PRs</div>
                        <div class="feature-description">Share your contributions</div>
                    </div>
                </div>

                <div class="feature-item">
                    <div class="feature-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Collaborate</div>
                        <div class="feature-description">Discuss with the team</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Start Guide -->
        <div class="quick-start">
            <div class="quick-start-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 2L15.09 8.26H22L17.55 12.5L19.64 18.74L12 14.49L4.36 18.74L6.45 12.5L2 8.26H8.91L12 2Z"></path>
                </svg>
                Quick Start Guide
            </div>
            <ol>
                <li><strong>Clone the repository:</strong> <code>git clone [repository-url]</code></li>
                <li><strong>Install dependencies:</strong> Check the README for setup instructions</li>
                <li><strong>Create a branch:</strong> <code>git checkout -b feature/your-feature</code></li>
                <li><strong>Make changes</strong> and commit your work</li>
                <li><strong>Push & create a pull request</strong> for review</li>
            </ol>
        </div>

        <!-- CTA Section -->
        <div class="cta-section">
            <a th:href="${acceptUrl}" class="cta-button">
                <svg style="width: 16px; height: 16px; display: inline; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M9 11l3 3L22 4"></path>
                    <path d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                Accept Invitation
            </a>
            <p class="cta-text">Click the button above or copy the link below to accept this invitation</p>
        </div>

        <!-- Invitation Link -->
        <p style="font-size: 13px; color: #666; margin-bottom: 8px;">
            <strong>Invitation Link:</strong>
        </p>
        <div class="invitation-link" th:text="${acceptUrl}">[Invitation URL]</div>

        <!-- Expires Note -->
        <div class="expires-note">
            <span class="expires-icon">⏰</span>
            <strong>This invitation expires on <span th:text="${#dates.format(expiresAt, 'MMM dd, yyyy')}">February 15, 2024</span></strong>
            <br>
            Accept before it expires to maintain access!
        </div>

        <!-- Additional Info -->
        <div style="background: #f5f7fa; padding: 20px; border-radius: 8px; margin: 24px 0; border: 1px solid #e0e0e0;">
            <div style="font-weight: 700; color: #0a3d62; font-size: 14px; margin-bottom: 12px;">
                📚 Need Help?
            </div>
            <p style="font-size: 13px; color: #666666; line-height: 1.6; margin: 0;">
                Refer to the project documentation in the README file, or contact <span th:text="${inviterName}">the project lead</span> if you have any questions about the setup or contribution guidelines.
            </p>
        </div>
    </div>

    <!-- Footer -->
    <div class="email-footer">
        <div class="footer-message">
            <strong>Welcome to the team! 🎉</strong><br>
            We're excited to collaborate with you on this project.
        </div>

        <div class="footer-divider"></div>

        <div class="footer-contact">
            <strong>Kandahar University<br>Faculty of Economics</strong>
            Email: <strong>dev@kandahar-university.edu</strong><br>
            Phone: <strong>+93 (0) XXX-XXX-XXXX</strong><br>
            Website: <strong>www.kandahar-university.edu</strong>
        </div>

        <div class="social-links">
            <a href="#" class="social-link" title="GitHub">
                <svg viewBox="0 0 24 24">
                    <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v 3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Email">
                <svg viewBox="0 0 24 24">
                    <path d="M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Slack">
                <svg viewBox="0 0 24 24">
                    <path d="M5 7c0 1.1-.9 2-2 2S1 8.1 1 7s.9-2 2-2 2 .9 2 2zm14-2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zM5 1C3.9 1 3 1.9 3 3v8c0 1.1.9 2 2 2s2-.9 2-2V3c0-1.1-.9-2-2-2zm6 2c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2s2-.9 2-2V5c0-1.1-.9-2-2-2z"></path>
                </svg>
            </a>
        </div>

        <div class="copyright">
            © 2024 Kandahar University - Faculty of Economics. All rights reserved.
        </div>
    </div>
</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\password-reset.html`

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\repository-notification.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:text="${emailTitle}">Repository Notification</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            background: #f5f7fa;
            font-family: Arial, Helvetica, sans-serif;
            color: #1a1a1a;
        }

        .container {
            max-width: 620px;
            margin: 24px auto;
            background: #ffffff;
            border-radius: 14px;
            overflow: hidden;
            box-shadow: 0 6px 18px rgba(10, 61, 98, 0.10);
        }

        .header {
            background: linear-gradient(135deg, #0a3d62, #1a5f7a);
            padding: 36px 28px;
            text-align: center;
        }

        .header-icon {
            width: 54px;
            height: 54px;
            margin: 0 auto 14px;
            border-radius: 50%;
            background: rgba(232, 180, 77, 0.15);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .header-icon svg {
            width: 30px;
            height: 30px;
            stroke: #e8b44d;
        }

        .header h1 {
            margin: 0;
            color: #e8b44d;
            font-size: 28px;
            font-weight: 700;
        }

        .header p {
            margin: 8px 0 0;
            color: #ffffff;
            font-size: 14px;
        }

        .content {
            padding: 32px 28px;
        }

        .text {
            font-size: 15px;
            color: #666666;
            line-height: 1.7;
            margin-bottom: 18px;
        }

        .strong {
            color: #0a3d62;
            font-weight: 700;
        }

        .repo-card {
            border: 1px solid #e4e8ee;
            border-left: 5px solid #e8b44d;
            border-radius: 12px;
            padding: 20px;
            background: #f9fbfd;
            margin: 22px 0;
        }

        .label {
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: #666666;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .repo-name {
            color: #0a3d62;
            font-size: 18px;
            font-weight: 700;
            margin: 0;
        }

        .actor-box {
            display: flex;
            gap: 14px;
            align-items: center;
            background: #f5f7fa;
            border: 1px solid #e4e8ee;
            border-radius: 12px;
            padding: 16px;
            margin: 22px 0;
        }

        .avatar {
            min-width: 46px;
            width: 46px;
            height: 46px;
            background: #0a3d62;
            color: #e8b44d;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 18px;
        }

        .actor-name {
            color: #0a3d62;
            font-weight: 700;
            font-size: 15px;
        }

        .actor-email {
            color: #666666;
            font-size: 13px;
            margin-top: 2px;
        }

        .details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
            margin: 22px 0;
        }

        .detail-item {
            background: #f5f7fa;
            border: 1px solid #e4e8ee;
            border-radius: 10px;
            padding: 14px;
        }

        .detail-label {
            color: #777777;
            font-size: 11px;
            text-transform: uppercase;
            font-weight: 700;
            margin-bottom: 4px;
        }

        .detail-value {
            color: #0a3d62;
            font-size: 14px;
            font-weight: 700;
            word-break: break-word;
        }

        .commit-box {
            background: #ffffff;
            border: 1px solid #e4e8ee;
            border-radius: 12px;
            padding: 18px;
            margin: 22px 0;
        }

        .commit-title {
            color: #0a3d62;
            font-weight: 700;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .commit-message {
            color: #666666;
            font-size: 14px;
            line-height: 1.7;
        }

        .cta {
            text-align: center;
            margin: 30px 0 18px;
        }

        .cta a {
            background: #0a3d62;
            color: #ffffff;
            text-decoration: none;
            padding: 14px 34px;
            border-radius: 8px;
            display: inline-block;
            font-weight: 700;
            font-size: 14px;
        }

        .note {
            font-size: 13px;
            color: #666666;
            background: rgba(232, 180, 77, 0.10);
            border: 1px solid rgba(232, 180, 77, 0.35);
            border-radius: 10px;
            padding: 14px;
            margin-top: 22px;
        }

        .footer {
            background: #f5f7fa;
            border-top: 1px solid #e4e8ee;
            padding: 24px 28px;
            text-align: center;
            color: #666666;
            font-size: 13px;
            line-height: 1.7;
        }

        .footer strong {
            color: #0a3d62;
        }

        .social {
            margin-top: 14px;
        }

        .social a {
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            margin: 0 6px;
            font-size: 13px;
        }

        @media (max-width: 640px) {
            .container {
                margin: 0;
                border-radius: 0;
            }

            .header {
                padding: 30px 20px;
            }

            .header h1 {
                font-size: 24px;
            }

            .content {
                padding: 24px 20px;
            }

            .details {
                grid-template-columns: 1fr;
            }

            .actor-box {
                align-items: flex-start;
            }

            .cta a {
                display: block;
                padding: 14px 18px;
            }
        }
    </style>
</head>

<body>
<div class="container">

    <div class="header">
        <div class="header-icon">
            <svg th:if="${eventType == 'REPOSITORY_PUSHED' || eventType == 'COMMIT_CREATED'}"
                 viewBox="0 0 24 24" fill="none" stroke-width="2">
                <path d="M12 19V5"/>
                <path d="M5 12l7-7 7 7"/>
            </svg>

            <svg th:if="${eventType == 'REPOSITORY_INVITATION_SENT'}"
                 viewBox="0 0 24 24" fill="none" stroke-width="2">
                <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                <circle cx="9" cy="7" r="4"/>
                <path d="M19 8v6"/>
                <path d="M22 11h-6"/>
            </svg>

            <svg th:if="${eventType == 'BRANCH_CREATED' || eventType == 'BRANCH_MERGED'}"
                 viewBox="0 0 24 24" fill="none" stroke-width="2">
                <circle cx="6" cy="6" r="3"/>
                <circle cx="18" cy="18" r="3"/>
                <path d="M6 9v3a6 6 0 0 0 6 6h3"/>
                <path d="M6 9v9"/>
            </svg>

            <svg th:if="${eventType == 'PULL_REQUEST_OPENED' || eventType == 'PULL_REQUEST_MERGED'}"
                 viewBox="0 0 24 24" fill="none" stroke-width="2">
                <circle cx="6" cy="18" r="3"/>
                <circle cx="18" cy="6" r="3"/>
                <path d="M6 15V6"/>
                <path d="M18 9v1a5 5 0 0 1-5 5H9"/>
            </svg>
        </div>

        <h1 th:text="${headerTitle}">Repository Update</h1>
        <p th:text="${headerSubtitle}">There is a new repository activity.</p>
    </div>

    <div class="content">
        <p class="text">
            Hello <span class="strong" th:text="${recipientName}">User</span>,
        </p>

        <p class="text" th:text="${mainMessage}">
            A repository activity happened.
        </p>

        <div class="repo-card">
            <div class="label">Repository</div>
            <p class="repo-name" th:text="${repositoryName}">repository-name</p>
        </div>

        <div class="actor-box" th:if="${actorName != null}">
            <div class="avatar" th:text="${actorName.substring(0,1).toUpperCase()}">A</div>
            <div>
                <div class="actor-name" th:text="${actorName}">Actor Name</div>
                <div class="actor-email" th:text="${actorEmail}">actor@example.com</div>
            </div>
        </div>

        <div class="details">
            <div class="detail-item" th:if="${branchName != null}">
                <div class="detail-label">Branch</div>
                <div class="detail-value" th:text="${branchName}">main</div>
            </div>

            <div class="detail-item" th:if="${sourceBranch != null}">
                <div class="detail-label">Source Branch</div>
                <div class="detail-value" th:text="${sourceBranch}">feature/login</div>
            </div>

            <div class="detail-item" th:if="${targetBranch != null}">
                <div class="detail-label">Target Branch</div>
                <div class="detail-value" th:text="${targetBranch}">main</div>
            </div>

            <div class="detail-item" th:if="${commitCount != null}">
                <div class="detail-label">Commits</div>
                <div class="detail-value" th:text="${commitCount}">3</div>
            </div>

            <div class="detail-item" th:if="${pullRequestTitle != null}">
                <div class="detail-label">Pull Request</div>
                <div class="detail-value" th:text="${pullRequestTitle}">PR title</div>
            </div>

            <div class="detail-item" th:if="${occurredAt != null}">
                <div class="detail-label">Date</div>
                <div class="detail-value" th:text="${occurredAt}">Today</div>
            </div>
        </div>

        <div class="commit-box" th:if="${commitMessage != null}">
            <div class="commit-title">Commit Message</div>
            <div class="commit-message" th:text="${commitMessage}">
                Commit message here.
            </div>
        </div>

        <div class="cta">
            <a th:href="${repositoryUrl}">Open Repository</a>
        </div>

        <div class="note">
            You are receiving this notification because you are a member of this repository.
        </div>
    </div>

    <div class="footer">
        <strong>Kandahar University — Faculty of Economics</strong><br>
        Kandahar, Afghanistan<br>
        Email: economics@kandahar-university.edu<br>

        <div class="social">
            <a href="#">Facebook</a>
            <a href="#">LinkedIn</a>
            <a href="#">Website</a>
        </div>
    </div>

</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\user-registered.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Kandahar University - Economy Faculty</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            line-height: 1.6;
            color: #1a1a1a;
        }

        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.08);
        }

        /* Header Section */
        .email-header {
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            padding: 40px 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .email-header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 300px;
            height: 300px;
            background: rgba(232, 180, 77, 0.08);
            border-radius: 50%;
        }

        .email-header::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 250px;
            height: 250px;
            background: rgba(232, 180, 77, 0.06);
            border-radius: 50%;
        }

        .university-header {
            position: relative;
            z-index: 1;
        }

        .university-name {
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            font-weight: 700;
            color: #e8b44d;
            letter-spacing: -0.5px;
            margin-bottom: 8px;
        }

        .faculty-name {
            font-size: 13px;
            font-weight: 600;
            color: rgba(255, 255, 255, 0.95);
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        .welcome-badge {
            display: inline-block;
            background-color: #e8b44d;
            color: #0a3d62;
            padding: 8px 24px;
            border-radius: 24px;
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            margin-top: 16px;
            letter-spacing: 1px;
        }

        /* Content Section */
        .email-content {
            padding: 40px 30px;
        }

        .greeting-title {
            font-family: 'Playfair Display', serif;
            font-size: 28px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 24px;
            line-height: 1.2;
        }

        .greeting-text {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
            margin-bottom: 20px;
        }

        .student-name {
            font-weight: 700;
            color: #0a3d62;
        }

        .highlight-box {
            background: linear-gradient(135deg, rgba(26, 95, 122, 0.05) 0%, rgba(232, 180, 77, 0.08) 100%);
            border-left: 4px solid #e8b44d;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
        }

        .highlight-title {
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 8px;
            font-size: 16px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .highlight-text {
            font-size: 14px;
            color: #666666;
            line-height: 1.7;
        }

        /* Icon Styles */
        .icon-small {
            width: 20px;
            height: 20px;
            display: inline-block;
        }

        /* Info Cards */
        .info-cards {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
            margin: 24px 0;
        }

        .info-card {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #e0e0e0;
            text-align: center;
        }

        .info-card-label {
            font-size: 11px;
            color: #666666;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .info-card-value {
            font-family: 'Playfair Display', serif;
            font-size: 18px;
            color: #0a3d62;
            font-weight: 700;
        }

        /* Features Section */
        .features-section {
            margin: 32px 0;
        }

        .section-title {
            font-family: 'Playfair Display', serif;
            font-size: 20px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #e8b44d;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .section-title svg {
            width: 24px;
            height: 24px;
        }

        .features-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
        }

        .feature-item {
            display: flex;
            gap: 12px;
            padding: 16px;
            background: #f5f7fa;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
        }

        .feature-icon-container {
            flex-shrink: 0;
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .feature-icon-container svg {
            width: 22px;
            height: 22px;
            color: #e8b44d;
            stroke: #e8b44d;
        }

        .feature-text {
            flex: 1;
        }

        .feature-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
            margin-bottom: 4px;
        }

        .feature-description {
            font-size: 12px;
            color: #666666;
            line-height: 1.5;
        }

        /* CTA Section */
        .cta-section {
            text-align: center;
            margin: 32px 0;
            padding: 32px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            color: #ffffff;
            padding: 16px 40px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.2);
        }

        .cta-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(10, 61, 98, 0.3);
        }

        .cta-text {
            font-size: 13px;
            color: #666666;
            margin-top: 16px;
        }

        /* Verification Code */
        .verification-code {
            background: #f9f9f9;
            padding: 16px;
            border-left: 4px solid #0a3d62;
            margin: 20px 0;
            font-family: 'Courier New', monospace;
            color: #0a3d62;
            font-size: 13px;
            word-break: break-all;
            border-radius: 4px;
            font-weight: 600;
        }

        /* Next Steps */
        .next-steps {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
        }

        .next-steps ol {
            margin-left: 20px;
            color: #666666;
        }

        .next-steps ol li {
            margin-bottom: 10px;
            font-size: 14px;
            line-height: 1.6;
        }

        .next-steps strong {
            color: #0a3d62;
        }

        /* Footer Section */
        .email-footer {
            background-color: #f5f7fa;
            padding: 32px 30px;
            text-align: center;
            border-top: 1px solid #e0e0e0;
        }

        .footer-message {
            font-size: 14px;
            color: #666666;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .footer-divider {
            height: 1px;
            background-color: #e0e0e0;
            margin: 20px 0;
        }

        .footer-contact {
            font-size: 13px;
            color: #666666;
            margin-bottom: 20px;
            line-height: 1.8;
        }

        .footer-contact strong {
            color: #0a3d62;
            display: block;
            margin-bottom: 8px;
        }

        .social-links {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin: 20px 0;
        }

        .social-link {
            display: inline-flex;
            width: 36px;
            height: 36px;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 50%;
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
            font-size: 14px;
        }

        .social-link:hover {
            background-color: #0a3d62;
            color: #ffffff;
            border-color: #0a3d62;
        }

        .social-link svg {
            width: 18px;
            height: 18px;
            stroke: currentColor;
            fill: none;
            stroke-width: 2;
        }

        .copyright {
            font-size: 11px;
            color: #999999;
            margin-top: 20px;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .email-container {
                border-radius: 0;
                margin: 0;
            }

            .email-header {
                padding: 30px 20px;
            }

            .email-content {
                padding: 24px 20px;
            }

            .email-footer {
                padding: 24px 20px;
            }

            .greeting-title {
                font-size: 24px;
            }

            .university-name {
                font-size: 28px;
            }

            .features-grid {
                grid-template-columns: 1fr;
                gap: 12px;
            }

            .info-cards {
                grid-template-columns: 1fr;
            }

            .section-title {
                font-size: 18px;
            }

            .feature-item {
                padding: 12px;
            }

            .cta-button {
                padding: 14px 32px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- Header -->
    <div class="email-header">
        <div class="university-header">
            <div class="university-name">🎓 Kandahar University</div>
            <div class="faculty-name">Faculty of Economics</div>
        </div>
        <div class="welcome-badge">Welcome to Our Platform</div>
    </div>

    <!-- Content -->
    <div class="email-content">
        <div class="greeting-title">Welcome, <span th:text="${firstName}" class="student-name">Friend</span>!</div>

        <p class="greeting-text">
            Thank you for registering with us! We're thrilled to have you join the Faculty of Economics at Kandahar University. Your account has been successfully created, and you're all set to begin your educational journey.
        </p>

        <div class="highlight-box">
            <div class="highlight-title">
                <svg class="icon-small" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"></path>
                    <path d="M12 6v6l4 2"></path>
                </svg>
                Your Account is Active
            </div>
            <div class="highlight-text">
                Your student account is now ready to use. You have access to all course materials, university resources, and learning platforms.
            </div>
        </div>

        <!-- Student Info Cards -->
        <div class="info-cards">
            <div class="info-card">
                <div class="info-card-label">Student Email</div>
                <div class="info-card-value" th:text="${studentEmail}">your.email@university.edu</div>
            </div>
            <div class="info-card">
                <div class="info-card-label">Registration Status</div>
                <div class="info-card-value" style="color: #27ae60;">✓ Confirmed</div>
            </div>
        </div>

        <!-- Verification CTA -->
        <div class="cta-section">
            <p style="font-size: 14px; color: #666; margin-bottom: 16px;">
                Please verify your email address to activate all features:
            </p>
            <a th:href="${verificationUrl}" class="cta-button">
                <svg style="width: 16px; height: 16px; display: inline; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M9 11l3 3L22 4"></path>
                    <path d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                Verify Your Email
            </a>
            <p class="cta-text">Click the button above or copy the link below:</p>
        </div>

        <div class="verification-code" th:text="${verificationUrl}">[Verification URL]</div>

        <p style="font-size: 13px; color: #999; margin-top: 20px;">
            <strong>Note:</strong> This link will expire in 24 hours. If you didn't create this account, please contact our support team immediately.
        </p>

        <!-- What You Can Do -->
        <div class="features-section">
            <div class="section-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 2L15.09 8.26H22L17.55 12.5L19.64 18.74L12 14.49L4.36 18.74L6.45 12.5L2 8.26H8.91L12 2Z"></path>
                </svg>
                What's Next?
            </div>
            <div class="features-grid">
                <div class="feature-item">
                    <div class="feature-icon-container">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
                            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Browse Courses</div>
                        <div class="feature-description">Explore available courses and enroll in classes</div>
                    </div>
                </div>

                <div class="feature-item">
                    <div class="feature-icon-container">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                            <circle cx="9" cy="7" r="4"></circle>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Connect with Peers</div>
                        <div class="feature-description">Join study groups and connect with classmates</div>
                    </div>
                </div>

                <div class="feature-item">
                    <div class="feature-icon-container">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="1"></circle>
                            <path d="M12 1v6m0 6v6"></path>
                            <path d="M4.22 4.22l4.24 4.24m6.08 0l4.24-4.24"></path>
                            <path d="M1 12h6m6 0h6"></path>
                            <path d="M4.22 19.78l4.24-4.24m6.08 0l4.24 4.24"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">View Schedule</div>
                        <div class="feature-description">Check your class times and academic calendar</div>
                    </div>
                </div>

                <div class="feature-item">
                    <div class="feature-icon-container">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                        </svg>
                    </div>
                    <div class="feature-text">
                        <div class="feature-title">Get Support</div>
                        <div class="feature-description">Contact advisors and get help anytime</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Next Steps List -->
        <div class="next-steps">
            <strong style="color: #0a3d62; display: block; margin-bottom: 12px;">📋 Complete These Steps:</strong>
            <ol>
                <li><strong>Verify</strong> your email address (link above)</li>
                <li><strong>Complete</strong> your student profile with personal information</li>
                <li><strong>Select</strong> your courses and preferred schedule</li>
                <li><strong>Attend</strong> orientation session (details in your portal)</li>
                <li><strong>Connect</strong> with your academic advisor</li>
            </ol>
        </div>

        <p class="greeting-text" style="margin-top: 24px;">
            If you have any questions or need assistance, our support team is always ready to help. Don't hesitate to reach out!
        </p>
    </div>

    <!-- Footer -->
    <div class="email-footer">
        <div class="footer-message">
            <strong>We're excited to have you on board!</strong><br>
            Your success is our priority. Welcome to the Kandahar University community.
        </div>

        <div class="footer-divider"></div>

        <div class="footer-contact">
            <strong>Kandahar University<br>Faculty of Economics</strong>
            Email: <strong>economics@kandahar-university.edu</strong><br>
            Phone: <strong>+93 (0) XXX-XXX-XXXX</strong><br>
            Website: <strong>www.kandahar-university.edu</strong>
        </div>

        <div class="social-links">
            <a href="#" class="social-link" title="Facebook">
                <svg viewBox="0 0 24 24">
                    <path d="M18 2h-3a6 6 0 0 0-6 6v3H7v4h2v8h4v-8h3l1-4h-4V8a1 1 0 0 1 1-1h3z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Twitter">
                <svg viewBox="0 0 24 24">
                    <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2s9 5 20 5a9.5 9.5 0 00-9-5.5c4.75 2.25 7-7 7-7"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="LinkedIn">
                <svg viewBox="0 0 24 24">
                    <path d="M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z"></path>
                    <circle cx="4" cy="4" r="2"></circle>
                </svg>
            </a>
        </div>

        <div class="copyright">
            © 2024 Kandahar University - Faculty of Economics. All rights reserved.
        </div>
    </div>
</div>
</body>
</html>
```

---

## `notification-service\src\main\resources\templates\email\system-invitation.html`

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>You're Invited to Kandahar University</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;800&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            line-height: 1.6;
            color: #1a1a1a;
        }

        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.08);
        }

        /* Header Section */
        .email-header {
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            padding: 40px 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .email-header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 300px;
            height: 300px;
            background: rgba(232, 180, 77, 0.08);
            border-radius: 50%;
        }

        .email-header::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 250px;
            height: 250px;
            background: rgba(232, 180, 77, 0.06);
            border-radius: 50%;
        }

        .header-icon {
            position: relative;
            z-index: 1;
            font-size: 48px;
            margin-bottom: 16px;
        }

        .header-title {
            position: relative;
            z-index: 1;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
            font-weight: 700;
            color: #e8b44d;
            letter-spacing: -0.5px;
            margin-bottom: 8px;
        }

        .header-subtitle {
            position: relative;
            z-index: 1;
            font-size: 13px;
            font-weight: 600;
            color: rgba(255, 255, 255, 0.95);
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        /* Content Section */
        .email-content {
            padding: 40px 30px;
        }

        .greeting-text {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
            margin-bottom: 24px;
        }

        .invitee-name {
            font-weight: 700;
            color: #0a3d62;
        }

        /* Invitation Card */
        .invitation-card {
            background: linear-gradient(135deg, rgba(232, 180, 77, 0.08) 0%, rgba(26, 95, 122, 0.05) 100%);
            border: 2px solid #e8b44d;
            border-radius: 12px;
            padding: 32px;
            margin: 32px 0;
            position: relative;
            text-align: center;
        }

        .invitation-card::before {
            content: '';
            position: absolute;
            top: -16px;
            left: 50%;
            transform: translateX(-50%);
            width: 32px;
            height: 32px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
        }

        .invitation-card-title {
            font-family: 'Playfair Display', serif;
            font-size: 24px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 12px;
            margin-top: 12px;
        }

        .invitation-card-role {
            font-size: 13px;
            font-weight: 600;
            color: #666666;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 16px;
        }

        .invitation-message {
            font-size: 15px;
            line-height: 1.8;
            color: #666666;
        }

        .invitation-message strong {
            color: #0a3d62;
        }

        /* Inviter Info */
        .inviter-section {
            background: #f5f7fa;
            padding: 24px;
            border-radius: 12px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
            display: grid;
            grid-template-columns: 60px 1fr;
            gap: 16px;
            align-items: center;
        }

        .inviter-avatar {
            width: 60px;
            height: 60px;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            font-weight: 700;
            color: #e8b44d;
        }

        .inviter-info h3 {
            font-size: 15px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 4px;
        }

        .inviter-info p {
            font-size: 13px;
            color: #666666;
            margin-bottom: 4px;
        }

        .inviter-role {
            font-size: 12px;
            color: #999999;
            font-weight: 600;
        }

        /* Features of Joining */
        .features-section {
            margin: 32px 0;
        }

        .section-title {
            font-family: 'Playfair Display', serif;
            font-size: 20px;
            font-weight: 700;
            color: #0a3d62;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #e8b44d;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .section-title svg {
            width: 24px;
            height: 24px;
        }

        .benefits-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
        }

        .benefit-item {
            display: flex;
            gap: 12px;
            padding: 16px;
            background: #f5f7fa;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
        }

        .benefit-icon {
            flex-shrink: 0;
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, #e8b44d 0%, #d4992f 100%);
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .benefit-icon svg {
            width: 22px;
            height: 22px;
            color: #ffffff;
            stroke: #ffffff;
        }

        .benefit-text {
            flex: 1;
        }

        .benefit-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 14px;
            margin-bottom: 4px;
        }

        .benefit-description {
            font-size: 12px;
            color: #666666;
            line-height: 1.5;
        }

        /* CTA Section */
        .cta-section {
            text-align: center;
            margin: 32px 0;
            padding: 32px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #0a3d62 0%, #1a5f7a 100%);
            color: #ffffff;
            padding: 16px 48px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(10, 61, 98, 0.2);
            cursor: pointer;
        }

        .cta-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(10, 61, 98, 0.3);
        }

        .cta-text {
            font-size: 13px;
            color: #666666;
            margin-top: 16px;
        }

        /* Invitation Link */
        .invitation-link {
            background: #f9f9f9;
            padding: 16px;
            border-left: 4px solid #0a3d62;
            margin: 20px 0;
            font-family: 'Courier New', monospace;
            color: #0a3d62;
            font-size: 12px;
            word-break: break-all;
            border-radius: 4px;
            font-weight: 600;
            overflow-wrap: break-word;
        }

        /* Expires Note */
        .expires-note {
            background: linear-gradient(135deg, rgba(232, 180, 77, 0.1) 0%, rgba(232, 180, 77, 0.05) 100%);
            border-left: 4px solid #e8b44d;
            padding: 16px;
            margin: 20px 0;
            border-radius: 4px;
            font-size: 13px;
            color: #0a3d62;
        }

        .expires-note strong {
            color: #0a3d62;
        }

        .expires-icon {
            display: inline-block;
            margin-right: 8px;
        }

        /* Additional Info */
        .info-box {
            background: #f5f7fa;
            padding: 20px;
            border-radius: 8px;
            margin: 24px 0;
            border: 1px solid #e0e0e0;
        }

        .info-box-title {
            font-weight: 700;
            color: #0a3d62;
            font-size: 15px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .info-box-title svg {
            width: 18px;
            height: 18px;
        }

        .info-box p {
            font-size: 14px;
            color: #666666;
            line-height: 1.7;
            margin: 0;
        }

        /* Footer Section */
        .email-footer {
            background-color: #f5f7fa;
            padding: 32px 30px;
            text-align: center;
            border-top: 1px solid #e0e0e0;
        }

        .footer-message {
            font-size: 14px;
            color: #666666;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .footer-divider {
            height: 1px;
            background-color: #e0e0e0;
            margin: 20px 0;
        }

        .footer-contact {
            font-size: 13px;
            color: #666666;
            margin-bottom: 20px;
            line-height: 1.8;
        }

        .footer-contact strong {
            color: #0a3d62;
            display: block;
            margin-bottom: 8px;
        }

        .social-links {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin: 20px 0;
        }

        .social-link {
            display: inline-flex;
            width: 36px;
            height: 36px;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 50%;
            color: #0a3d62;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .social-link:hover {
            background-color: #0a3d62;
            color: #ffffff;
            border-color: #0a3d62;
        }

        .social-link svg {
            width: 18px;
            height: 18px;
            stroke: currentColor;
            fill: none;
            stroke-width: 2;
        }

        .copyright {
            font-size: 11px;
            color: #999999;
            margin-top: 20px;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .email-container {
                border-radius: 0;
                margin: 0;
            }

            .email-header {
                padding: 30px 20px;
            }

            .email-content {
                padding: 24px 20px;
            }

            .email-footer {
                padding: 24px 20px;
            }

            .header-title {
                font-size: 28px;
            }

            .header-icon {
                font-size: 40px;
            }

            .invitation-card {
                padding: 24px;
            }

            .invitation-card-title {
                font-size: 20px;
            }

            .benefits-grid {
                grid-template-columns: 1fr;
                gap: 12px;
            }

            .inviter-section {
                grid-template-columns: 50px 1fr;
                gap: 12px;
            }

            .inviter-avatar {
                width: 50px;
                height: 50px;
                font-size: 20px;
            }

            .section-title {
                font-size: 18px;
            }

            .cta-button {
                padding: 14px 32px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- Header -->
    <div class="email-header">
        <div class="header-icon">✨</div>
        <div class="header-title">You're Invited!</div>
        <div class="header-subtitle">Join Kandahar University</div>
    </div>

    <!-- Content -->
    <div class="email-content">
        <p class="greeting-text">
            Hello <span class="invitee-name" th:text="${inviteeName != null ? inviteeName : 'there'}">there</span>,
        </p>

        <!-- Invitation Card -->
        <div class="invitation-card">
            <div class="invitation-card-title" th:text="${inviterName}">Dr. Ahmad Hassan</div>
            <div class="invitation-card-role" th:text="${inviterRole}">Faculty Dean</div>
            <p class="invitation-message">
                has invited you to join<br>
                <strong th:text="${targetName}">Kandahar University - Faculty of Economics</strong>
            </p>
        </div>

        <!-- Inviter Section -->
        <div class="inviter-section">
            <div class="inviter-avatar" th:text="${inviterName.charAt(0)}">A</div>
            <div class="inviter-info">
                <h3 th:text="${inviterName}">Dr. Ahmad Hassan</h3>
                <p th:text="${inviterEmail}">ahmad.hassan@kandahar-university.edu</p>
                <p class="inviter-role" th:text="${inviterRole}">Faculty Dean</p>
            </div>
        </div>

        <!-- What You Get -->
        <div class="features-section">
            <div class="section-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path>
                    <polyline points="13 2 13 9 20 9"></polyline>
                </svg>
                What You'll Get Access To
            </div>
            <div class="benefits-grid">
                <div class="benefit-item">
                    <div class="benefit-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
                            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
                        </svg>
                    </div>
                    <div class="benefit-text">
                        <div class="benefit-title">Course Materials</div>
                        <div class="benefit-description">Access all lecture notes and learning resources</div>
                    </div>
                </div>

                <div class="benefit-item">
                    <div class="benefit-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                            <circle cx="9" cy="7" r="4"></circle>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                        </svg>
                    </div>
                    <div class="benefit-text">
                        <div class="benefit-title">Collaborate</div>
                        <div class="benefit-description">Connect with peers and faculty members</div>
                    </div>
                </div>

                <div class="benefit-item">
                    <div class="benefit-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="1"></circle>
                            <path d="M12 1v6m0 6v6"></path>
                            <path d="M4.22 4.22l4.24 4.24m6.08 0l4.24-4.24"></path>
                            <path d="M1 12h6m6 0h6"></path>
                            <path d="M4.22 19.78l4.24-4.24m6.08 0l4.24 4.24"></path>
                        </svg>
                    </div>
                    <div class="benefit-text">
                        <div class="benefit-title">Class Schedule</div>
                        <div class="benefit-description">View timetables and academic calendar</div>
                    </div>
                </div>

                <div class="benefit-item">
                    <div class="benefit-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                        </svg>
                    </div>
                    <div class="benefit-text">
                        <div class="benefit-title">Support 24/7</div>
                        <div class="benefit-description">Get help from advisors anytime</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Why Join -->
        <div class="info-box">
            <div class="info-box-title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"></path>
                    <path d="M12 6v6l4 2"></path>
                </svg>
                Why Join Now?
            </div>
            <p>
                Be part of a prestigious academic community at Kandahar University's Faculty of Economics. Gain access to world-class education, network with industry professionals, and advance your career in economics.
            </p>
        </div>

        <!-- CTA Section -->
        <div class="cta-section">
            <a th:href="${acceptUrl}" class="cta-button">
                <svg style="width: 16px; height: 16px; display: inline; margin-right: 8px; vertical-align: -2px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M9 11l3 3L22 4"></path>
                    <path d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                Accept Invitation
            </a>
            <p class="cta-text">Click the button above or copy the link below to accept this invitation</p>
        </div>

        <!-- Invitation Link -->
        <p style="font-size: 13px; color: #666; margin-bottom: 8px;">
            <strong>Invitation Link:</strong>
        </p>
        <div class="invitation-link" th:text="${acceptUrl}">[Invitation URL]</div>

        <!-- Expires Note -->
        <div class="expires-note">
            <span class="expires-icon">⏰</span>
            <strong>This invitation expires on <span th:text="${#dates.format(expiresAt, 'MMM dd, yyyy')}">January 30, 2024</span></strong>
            <br>
            Don't miss out — accept it before it expires!
        </div>

        <!-- Additional Information -->
        <div class="info-box" style="background: linear-gradient(135deg, rgba(26, 95, 122, 0.05) 0%, rgba(232, 180, 77, 0.08) 100%); border: 1px solid #e8b44d;">
            <div class="info-box-title" style="color: #0a3d62;">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polyline points="12 6 12 12 16 14"></polyline>
                </svg>
                Questions?
            </div>
            <p>
                If you have any questions about this invitation or need assistance with the enrollment process, please don't hesitate to reach out to us. Our support team is here to help!
            </p>
        </div>
    </div>

    <!-- Footer -->
    <div class="email-footer">
        <div class="footer-message">
            <strong>We're excited to welcome you to our community!</strong><br>
            Accept this invitation and start your journey with us today.
        </div>

        <div class="footer-divider"></div>

        <div class="footer-contact">
            <strong>Kandahar University<br>Faculty of Economics</strong>
            Email: <strong>admissions@kandahar-university.edu</strong><br>
            Phone: <strong>+93 (0) XXX-XXX-XXXX</strong><br>
            Website: <strong>www.kandahar-university.edu</strong>
        </div>

        <div class="social-links">
            <a href="#" class="social-link" title="Facebook">
                <svg viewBox="0 0 24 24">
                    <path d="M18 2h-3a6 6 0 0 0-6 6v3H7v4h2v8h4v-8h3l1-4h-4V8a1 1 0 0 1 1-1h3z"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="Twitter">
                <svg viewBox="0 0 24 24">
                    <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2s9 5 20 5a9.5 9.5 0 00-9-5.5c4.75 2.25 7-7 7-7"></path>
                </svg>
            </a>
            <a href="#" class="social-link" title="LinkedIn">
                <svg viewBox="0 0 24 24">
                    <path d="M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z"></path>
                    <circle cx="4" cy="4" r="2"></circle>
                </svg>
            </a>
        </div>

        <div class="copyright">
            © 2024 Kandahar University - Faculty of Economics. All rights reserved.
        </div>
    </div>
</div>
</body>
</html>
```

---

## `notification-service\src\main\java\com\final_project\notification_service\websocket\WebSocketEvents.java`

```
package com.final_project.notification_service.websocket;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class WebSocketEvents {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WebSocketMessage {
        private String type;        // Event type
        private String repository;  // owner/repo
        private String branch;
        private String user;        // Username who triggered the event
        private Object payload;     // Event-specific data
        private Instant timestamp;
        private String messageId;   // Unique message ID for deduplication
    }

    // ─── Push Event ───────────────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PushEvent {
        private String ref;           // e.g., "refs/heads/main"
        private String before;        // Commit SHA before push
        private String after;         // Commit SHA after push
        private boolean created;      // New branch created?
        private boolean deleted;      // Branch deleted?
        private boolean forced;       // Force push?
        private List<CommitInfo> commits;
        private RepositoryInfo repository;
        private PusherInfo pusher;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitInfo {
        private String id;
        private String message;
        private String timestamp;
        private String author;
        private List<String> added;
        private List<String> modified;
        private List<String> removed;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepositoryInfo {
        private String name;
        private String owner;
        private String fullName;
        private String url;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PusherInfo {
        private String name;
        private String email;
    }

    // ─── Pull Request Events ──────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PullRequestEvent {
        private String action;        // "opened", "closed", "merged", "updated"
        private int number;
        private PullRequestInfo pullRequest;
        private RepositoryInfo repository;
        private UserInfo sender;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PullRequestInfo {
        private String id;
        private String title;
        private String body;
        private String state;         // "open", "closed", "merged"
        private String sourceBranch;
        private String targetBranch;
        private UserInfo author;
        private List<String> reviewers;
        private Instant createdAt;
        private Instant updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String username;
        private String avatarUrl;
        private String profileUrl;
    }

    // ─── File Change Events ───────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileChangeEvent {
        private String path;
        private String action;        // "created", "updated", "deleted", "renamed"
        private String previousPath;  // For renames
        private String content;       // New content (for real-time editing)
        private String diff;          // Unified diff
        private UserInfo editor;
        private Instant editedAt;
    }

    // ─── Collaboration Events ─────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollaborationEvent {
        private String filePath;
        private String editor;
        private CursorPosition cursor;
        private String selection;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CursorPosition {
        private int line;
        private int column;
    }

    // ─── Notification Events ──────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationEvent {
        private String id;
        private String type;          // "mention", "review", "comment", "invite"
        private String title;
        private String message;
        private String url;
        private UserInfo sender;
        private Instant createdAt;
        private boolean read;
    }

    // ─── Repository Activity ──────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepositoryActivity {
        private String type;          // "commit", "branch", "tag", "release"
        private String description;
        private UserInfo actor;
        private Instant timestamp;
        private Map<String, Object> metadata;
    }

    // ─── Live Edit Session ────────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LiveEditSession {
        private String sessionId;
        private String filePath;
        private String branch;
        private List<String> participants;
        private String content;
        private long version;         // For conflict resolution
        private Instant lastModified;
    }

    // ─── Merge Conflict Event ─────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MergeConflictEvent {
        private String filePath;
        private String baseContent;
        private String ourContent;
        private String theirContent;
        private List<ConflictRegion> conflicts;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConflictRegion {
        private int ourStartLine;
        private int ourEndLine;
        private int theirStartLine;
        private int theirEndLine;
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\websocket\WebSocketEventListener.java`

```
package com.final_project.notification_service.websocket;
import com.final_project.notification_service.service.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Map;

@Component
public class WebSocketEventListener {

    @Autowired
    private WebSocketNotificationService notificationService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        System.out.println("WebSocket Connected: " + username);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (sessionAttributes != null) {
            String username = (String) sessionAttributes.get("username");
            System.out.println("WebSocket Disconnected: " + username);

            // Clean up - remove user from any repos they were viewing
            // In production, you'd track which repos the user was viewing
        }
    }

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        System.out.println("Subscribed to: " + destination);
    }

    @EventListener
    public void handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        System.out.println("Unsubscribed from: " + destination);
    }
}
//// Add WebSocket notifications to PullRequestController
//
//@Autowired
//private WebSocketNotificationService notificationService;
//
//// Update create method
//@PostMapping(path = "/repos/{owner}/{repo}/pulls", ...)
//public ResponseEntity<PullRequestResponse> create(...) {
//    // ... existing code ...
//
//    PullRequestDocument pr = pullRequestApplicationService.create(...);
//
//    // Send WebSocket notification
//    PullRequestEvent prEvent = PullRequestEvent.builder()
//            .action("opened")
//            .pullRequest(PullRequestInfo.builder()
//                    .id(pr.getId().toHexString())
//                    .title(pr.getTitle())
//                    .body(pr.getDescription())
//                    .state("open")
//                    .sourceBranch(pr.getSourceBranch())
//                    .targetBranch(pr.getTargetBranch())
//                    .author(UserInfo.builder().username(user.getUsername()).build())
//                    .build())
//            .repository(RepositoryInfo.builder()
//                    .name(repo)
//                    .owner(owner)
//                    .fullName(owner + "/" + repo)
//                    .build())
//            .sender(UserInfo.builder().username(user.getUsername()).build())
//            .build();
//
//    notificationService.notifyPullRequest(owner, repo, prEvent);
//
//    return ResponseEntity.status(HttpStatus.CREATED).body(PullRequestResponse.from(pr));
//}


//// Add to the existing RepositoryController class:
//
//@Autowired
//private WebSocketNotificationService notificationService;
//
//// Update the updateBranch method to send WebSocket notification
//@PostMapping(
//        path = "/repos/{owner}/{repo}/refs/heads/{branch}",
//        consumes = MediaType.APPLICATION_JSON_VALUE,
//        produces = MediaType.APPLICATION_JSON_VALUE
//)
//public UpdateBranchResponse updateBranch(
//        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
//        @PathVariable String owner,
//        @PathVariable String repo,
//        @PathVariable String branch,
//        @RequestBody UpdateBranchBody body
//) {
//    UserDocument user = authService.requireUser(authorization);
//    var meta = vicRepositoryService.loadMeta(owner, repo);
//
//    if (!RepoAccessRules.canWrite(meta, user.getUsername())) {
//        throw new ForbiddenException("forbidden");
//    }
//
//    if (body == null || body.hash() == null || body.hash().isBlank()) {
//        throw new BadRequestException("hash is required");
//    }
//
//    // Get old hash before update
//    String oldHash = meta.getBranchHeads().getOrDefault(branch, "");
//
//    vicRepositoryService.updateBranchRef(meta, branch, body.hash().trim());
//
//    // Send WebSocket notification
//    PushEvent pushEvent = PushEvent.builder()
//            .ref("refs/heads/" + branch)
//            .before(oldHash)
//            .after(body.hash().trim())
//            .created(oldHash.isEmpty())
//            .deleted(false)
//            .forced(false)
//            .repository(RepositoryInfo.builder()
//                    .name(repo)
//                    .owner(owner)
//                    .fullName(owner + "/" + repo)
//                    .build())
//            .pusher(PusherInfo.builder()
//                    .name(user.getUsername())
//                    .email(user.getEmail())
//                    .build())
//            .build();
//
//    notificationService.notifyPush(owner, repo, pushEvent);
//
//    return new UpdateBranchResponse("updated", branch.trim());
//}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\websocket\StompPrincipal.java`

```
package com.final_project.notification_service.websocket;


import java.security.Principal;

public class StompPrincipal implements Principal {
    private final String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}


```

---

## `notification-service\src\main\java\com\final_project\notification_service\websocket\AuthHandshakeInterceptor.java`

```
package com.final_project.notification_service.websocket;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private AuthService authService;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        // Extract token from query parameter or header
        String token = null;

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            token = servletRequest.getParameter("token");

            if (token == null) {
                // Try from Authorization header
                String authHeader = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
            }
        }

        // Authenticate user
        if (token != null) {
            Optional<UserDocument> user = authService.optionalUser("Bearer " + token);
            if (user.isPresent()) {
                attributes.put("username", user.get().getUsername());
                attributes.put("userId", user.get().getId().toString());
                attributes.put("authenticated", true);
                return true;
            }
        }

        // Allow anonymous connections for public repos
        attributes.put("authenticated", false);
        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
        // Post-handshake logic if needed
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\UserRegisteredEvent.java`

```
package com.final_project.notification_service.event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisteredEvent {
    private String eventId;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String verificationToken;
    private String registrationSource;      // WEB, MOBILE, API
    private LocalDateTime occurredAt;
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\RepositoryOperationEvent.java`

```
package com.final_project.notification_service.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.notification_service.model.RepositoryEventType;
import com.final_project.notification_service.model.RepositoryMemberRecipient;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryOperationEvent {

    private String eventId;
    private RepositoryEventType eventType;

    private String repositoryId;
    private String repositoryName;
    private String repositoryUrl;

    private String actorUserId;
    private String actorName;
    private String actorEmail;

    private String ownerUserId;
    private String ownerName;
    private String ownerEmail;

    private String branchName;
    private String sourceBranch;
    private String targetBranch;

    private String commitId;
    private String commitMessage;
    private Integer commitCount;

    private String pullRequestId;
    private String pullRequestTitle;
    private String pullRequestUrl;

    private String invitedUserId;
    private String invitedUserName;
    private String invitedUserEmail;

    private List<RepositoryMemberRecipient> recipients;

    private LocalDateTime occurredAt;

    private Map<String, Object> metadata;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\PasswordChangedEvent.java`

```
package com.final_project.notification_service.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordChangedEvent {
    private String eventId;
    private String userId;
    private String email;
    private String firstName;
    private String changeType;          // CHANGED, RESET
    private String ipAddress;           // for security notice
    private String userAgent;
    private LocalDateTime occurredAt;
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\InvitationSentEvent.java`

```
package com.final_project.notification_service.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvitationSentEvent {
    private String eventId;
    private String invitationId;
    private String inviteeEmail;
    private String inviteeName;
    private String inviterUserId;
    private String inviterName;
    private String invitationType;      // SYSTEM, REPOSITORY
    private String targetId;            // repoId or null for system invitations
    private String targetName;          // repo name or system name
    private String targetUrl;           // deep link for accept action
    private String invitationToken;
    private LocalDateTime expiresAt;
    private LocalDateTime occurredAt;
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\CommentRepliedEvent.java`

```
package com.final_project.notification_service.event;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRepliedEvent {
    private String eventId;
    private String replyId;
    private String parentCommentId;
    private String blogPostId;
    private String blogPostTitle;
    private String blogPostUrl;
    private String originalCommenterUserId;   // person who wrote the parent comment
    private String originalCommenterEmail;
    private String originalCommenterName;
    private String replierUserId;
    private String replierName;
    private String replySnippet;              // first 200 chars of reply
    private LocalDateTime occurredAt;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\BlogInteractionEvent.java`

```
package com.final_project.notification_service.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.notification_service.model.ArticleEventType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogInteractionEvent {

    private String eventId;
    private ArticleEventType eventType;
    private String blogPostId;
    private String blogPostTitle;
    private String blogPostUrl;

    private String authorUserId;
    private String authorEmail;
    private String authorName;

    private String actorUserId;
    private String actorName;
    private String actorEmail;

    private String commentId;
    private String parentCommentId;
    private String commentSnippet;

    private String sharePlatform;
    private String profile;
    private String adminUserId;
    private String adminName;
    private String adminEmail;

    private LocalDateTime occurredAt;

    private Map<String, Object> metadata;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\event\BlogCommentedEvent.java`

```
package com.final_project.notification_service.event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Event published when someone leaves a comment on a blog post.
 * Producer: blog-service  |  Topic: blog.commented
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogCommentedEvent {
    private String eventId;
    private String commentId;
    private String blogPostId;
    private String blogPostTitle;
    private String blogPostUrl;
    private String authorUserId;        // blog post owner to be notified
    private String authorEmail;
    private String authorName;
    private String commenterUserId;
    private String commenterName;
    private String commentSnippet;      // first 200 chars of comment
    private LocalDateTime occurredAt;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\exception\RateLimitExceededException.java`

```
package com.final_project.notification_service.exception;

public class RateLimitExceededException extends BaseException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\exception\NotificationNotFoundException.java`

```
package com.final_project.notification_service.exception;


import java.util.UUID;

public class NotificationNotFoundException extends BaseException {
    public NotificationNotFoundException(UUID id) {
        super("Notification not found: " + id);
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\exception\GlobalExceptionHandler.java`

```
package com.final_project.notification_service.exception;

import com.final_project.notification_service.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(
            NotificationNotFoundException ex, WebRequest request) {
        log.warn("NotificationNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage(), "NOTIFICATION_NOT_FOUND"));
    }

    @ExceptionHandler(DuplicateNotificationException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicate(
            DuplicateNotificationException ex, WebRequest request) {
        log.warn("DuplicateNotificationException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.failure(ex.getMessage(), "DUPLICATE_NOTIFICATION"));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleRateLimit(
            RateLimitExceededException ex, WebRequest request) {
        log.warn("RateLimitExceededException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(ApiResponse.failure(ex.getMessage(), "RATE_LIMIT_EXCEEDED"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(fieldName, message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("Validation failed", "VALIDATION_ERROR")
                        .toBuilder()
                        .error(ApiResponse.ErrorDetail.builder()
                                .code("VALIDATION_ERROR")
                                .fieldErrors(fieldErrors)
                                .build())
                        .build());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(
            BaseException ex, WebRequest request) {
        log.error("BaseException occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ex.getMessage(), "INTERNAL_ERROR"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(
            Exception ex, WebRequest request) {
        log.error("Unexpected exception occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("An unexpected error occurred", "INTERNAL_ERROR"));
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\exception\DuplicateNotificationException.java`

```
package com.final_project.notification_service.exception;


public class DuplicateNotificationException extends BaseException {
    public DuplicateNotificationException(String message) {
        super(message);
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\exception\BaseException.java`

```
package com.final_project.notification_service.exception;
public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\WebSocketNotificationService.java`

```
package com.final_project.notification_service.service;

import com.final_project.notification_service.websocket.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class WebSocketNotificationService {

    private SimpMessagingTemplate messagingTemplate;

    // Track active editing sessions
    private final Map<String, WebSocketEvents.LiveEditSession> activeEdits = new ConcurrentHashMap<>();

    // Track online users per repository
    private final Map<String, Map<String, Boolean>> repoViewers = new ConcurrentHashMap<>();

    /**
     * Broadcast push event to all repository watchers
     */
    public void notifyPush(String owner, String repo, WebSocketEvents.PushEvent event) {
        String destination = "/repo/" + owner + "/" + repo + "/push";
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("push")
                .repository(owner + "/" + repo)
                .user(event.getPusher().getName())
                .payload(event)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * Notify about pull request changes
     */
    public void notifyPullRequest(String owner, String repo, WebSocketEvents.PullRequestEvent event) {
        String destination = "/repo/" + owner + "/" + repo + "/pulls";
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("pull_request")
                .repository(owner + "/" + repo)
                .user(event.getSender().getUsername())
                .payload(event)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);

        // Also notify specific users (reviewers, author)
        if (event.getPullRequest().getReviewers() != null) {
            for (String reviewer : event.getPullRequest().getReviewers()) {
                messagingTemplate.convertAndSendToUser(
                        reviewer,
                        "/queue/pull-requests",
                        message
                );
            }
        }
    }

    /**
     * Broadcast file changes for real-time collaboration
     */
    public void notifyFileChange(String owner, String repo, String branch, WebSocketEvents.FileChangeEvent event) {
        String destination = "/repo/" + owner + "/" + repo + "/files/" + branch;
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("file_change")
                .repository(owner + "/" + repo)
                .branch(branch)
                .user(event.getEditor().getUsername())
                .payload(event)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * Send notification to specific user
     */
    public void sendUserNotification(String username, WebSocketEvents.NotificationEvent notification) {
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("notification")
                .user(username)
                .payload(notification)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/notifications",
                message
        );
    }

    /**
     * Broadcast repository activity
     */
    public void broadcastActivity(String owner, String repo, WebSocketEvents.RepositoryActivity activity) {
        String destination = "/repo/" + owner + "/" + repo + "/activity";
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("activity")
                .repository(owner + "/" + repo)
                .user(activity.getActor().getUsername())
                .payload(activity)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * Start or update live editing session
     */
    public void startLiveEdit(String owner, String repo, String branch, String filePath, String username) {
        String sessionKey = owner + "/" + repo + "/" + branch + "/" + filePath;

        WebSocketEvents.LiveEditSession session = activeEdits.computeIfAbsent(sessionKey, k ->
                WebSocketEvents.LiveEditSession.builder()
                        .sessionId(UUID.randomUUID().toString())
                        .filePath(filePath)
                        .branch(branch)
                        .build()
        );

        if (!session.getParticipants().contains(username)) {
            session.getParticipants().add(username);
        }

        // Notify others that user started editing
        WebSocketEvents.CollaborationEvent collabEvent = WebSocketEvents.CollaborationEvent.builder()
                .filePath(filePath)
                .editor(username)
                .build();

        String destination = "/repo/" + owner + "/" + repo + "/collaboration/" + branch;
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("user_joined_edit")
                        .repository(owner + "/" + repo)
                        .user(username)
                        .payload(collabEvent)
                        .build()
        );
    }

    /**
     * Update cursor position for collaborative editing
     */
    public void updateCursorPosition(String owner, String repo, String branch,
                                     String filePath, String username,
                                     int line, int column) {
        WebSocketEvents.CollaborationEvent event = WebSocketEvents.CollaborationEvent.builder()
                .filePath(filePath)
                .editor(username)
                .cursor(WebSocketEvents.CursorPosition.builder().line(line).column(column).build())
                .build();

        String destination = "/repo/" + owner + "/" + repo + "/cursors/" + branch;
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("cursor_update")
                        .repository(owner + "/" + repo)
                        .user(username)
                        .payload(event)
                        .build()
        );
    }

    /**
     * Notify about merge conflicts
     */
    public void notifyMergeConflict(String owner, String repo, String branch,
                                    WebSocketEvents.MergeConflictEvent conflict) {
        String destination = "/repo/" + owner + "/" + repo + "/merge/" + branch;
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("merge_conflict")
                        .repository(owner + "/" + repo)
                        .branch(branch)
                        .payload(conflict)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    /**
     * Track repository viewers (online users)
     */
    public void userJoinedRepo(String owner, String repo, String username) {
        String key = owner + "/" + repo;
        repoViewers.computeIfAbsent(key, k -> new ConcurrentHashMap<>())
                .put(username, true);

        // Broadcast updated viewer list
        broadcastViewerList(owner, repo);
    }

    public void userLeftRepo(String owner, String repo, String username) {
        String key = owner + "/" + repo;
        Map<String, Boolean> viewers = repoViewers.get(key);
        if (viewers != null) {
            viewers.remove(username);
            broadcastViewerList(owner, repo);
        }
    }

    private void broadcastViewerList(String owner, String repo) {
        String key = owner + "/" + repo;
        Map<String, Boolean> viewers = repoViewers.getOrDefault(key, new ConcurrentHashMap<>());

        String destination = "/repo/" + owner + "/" + repo + "/viewers";
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("viewers_update")
                        .repository(key)
                        .payload(Map.of("viewers", viewers.keySet()))
                        .timestamp(Instant.now())
                        .build()
        );
    }

    /**
     * Get active editing sessions for a repository
     */
    public Map<String, WebSocketEvents.LiveEditSession> getActiveEdits(String owner, String repo) {
        String prefix = owner + "/" + repo + "/";
        Map<String, WebSocketEvents.LiveEditSession> repoEdits = new ConcurrentHashMap<>();

        for (Map.Entry<String, WebSocketEvents.LiveEditSession> entry : activeEdits.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                repoEdits.put(entry.getKey(), entry.getValue());
            }
        }

        return repoEdits;
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\EmailService.java`

```
package com.final_project.notification_service.service;
import com.final_project.notification_service.config.AppProperties;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
//@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    public EmailService (JavaMailSender mailSender, TemplateEngine templateEngine, AppProperties appProperties){
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.appProperties = appProperties;
    }

    /**
     * Sends an HTML email rendered from a Thymeleaf template.
     *
     * @param to           recipient email address
     * @param subject      email subject
     * @param templateName Thymeleaf template file name (without .html extension)
     * @param variables    template context variables
     */
    @Async("notificationExecutor")
    @CircuitBreaker(name = "emailService", fallbackMethod = "emailFallback")
    @Retry(name = "emailService")
    public CompletableFuture<Void> sendHtmlEmail(
            String to,
            String subject,
            String templateName,
            Map<String, Object> variables
    ) {
        try {
            String html = renderTemplate(templateName, variables);
            sendMimeMessage(to, subject, html);
            log.info("Email sent successfully. To={} Subject={}", to, subject);
            return CompletableFuture.completedFuture(null);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email. To={} Subject={} Error={}", to, subject, e.getMessage());
            throw new RuntimeException("Email sending failed: " + e.getMessage(), e);
        }
    }

    /**
     * Sends a plain-text email (for fallback or simple alerts).
     */
    @Async("notificationExecutor")
    @CircuitBreaker(name = "emailService", fallbackMethod = "emailFallback")
    @Retry(name = "emailService")
    public CompletableFuture<Void> sendPlainTextEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(
                    appProperties.getNotification().getFromEmail(),
                    appProperties.getNotification().getFromName()
            );
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false);
            mailSender.send(message);
            log.info("Plain text email sent. To={}", to);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("Failed to send plain text email. To={} Error={}", to, e.getMessage());
            throw new RuntimeException("Plain text email failed: " + e.getMessage(), e);
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private String renderTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }

    private void sendMimeMessage(String to, String subject, String html) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(
                appProperties.getNotification().getFromEmail(),
                appProperties.getNotification().getFromName()
        );
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }

    // ── Circuit-breaker fallback ──────────────────────────────────────────────

    @SuppressWarnings("unused")
    private CompletableFuture<Void> emailFallback(
            String to, String subject, String templateName,
            Map<String, Object> variables, Throwable ex
    ) {
        log.error("Email circuit breaker OPEN. Falling back for To={} Subject={} Cause={}",
                to, subject, ex.getMessage());
        // In production: publish to DLQ or persist for deferred retry
        return CompletableFuture.failedFuture(
                new RuntimeException("Email service unavailable — notification queued for retry", ex));
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Void> emailFallback(
            String to, String subject, String body, Throwable ex
    ) {
        log.error("Plain email circuit breaker OPEN. To={} Cause={}", to, ex.getMessage());
        return CompletableFuture.failedFuture(
                new RuntimeException("Email service unavailable", ex));
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\response\PagedResponse.java`

```
package com.final_project.notification_service.dto.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paged response wrapper")
public class PagedResponse<T> {
    private List<T> content;
    @Schema(description = "Current page number (0-based)")
    private int page;

    @Schema(description = "Page size")
    private int size;

    @Schema(description = "Total number of elements")
    private long totalElements;

    @Schema(description = "Total number of pages")
    private int totalPages;

    @Schema(description = "Whether this is the last page")
    private boolean last;

    @Schema(description = "Whether this is the first page")
    private boolean first;

    public static <T> PagedResponse<T> of(Page<T> page) {
        return PagedResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .first(page.isFirst())
                .build();
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\response\NotificationStatsResponse.java`

```
package com.final_project.notification_service.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Aggregated notification statistics")
public class NotificationStatsResponse {

    @Schema(description = "Total notifications in the queried period")
    private long total;

    @Schema(description = "Count by status")
    private Map<String, Long> byStatus;

    @Schema(description = "Count by type")
    private Map<String, Long> byType;

    @Schema(description = "Delivery success rate (0–100)")
    private double successRate;

    @Schema(description = "Average retry count for failed notifications")
    private double averageRetries;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\response\NotificationResponse.java`

```
package com.final_project.notification_service.dto.response;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Notification response payload")
public class NotificationResponse {

    @Schema(description = "Unique notification ID")
    private UUID id;

    private String recipientUserId;
    private String recipientEmail;
    private String recipientName;

    private NotificationType type;
    private NotificationChannel channel;
    private NotificationStatus status;

    private String subject;
    private String body;

    private String referenceId;
    private String referenceType;

    private Integer retryCount;
    private Integer maxRetries;
    private String failureReason;

    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\response\ApiResponse.java`

```
package com.final_project.notification_service.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response envelope")
public class ApiResponse<T> {

    @Schema(description = "Whether the request succeeded")
    private boolean success;

    @Schema(description = "Human-readable message")
    private String message;

    @Schema(description = "Response payload")
    private T data;

    @Schema(description = "Error details — present only on failure")
    private ErrorDetail error;

    @Builder.Default
    @Schema(description = "Server timestamp (ISO-8601)")
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure(String message, String code) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(ErrorDetail.builder().code(code).build())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetail {
        private String code;
        private String details;
        private Map<String, String> fieldErrors;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\repo\NotificationRepository.java`

```
package com.final_project.notification_service.repo;

import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // ── Idempotency ──────────────────────────────────────────────────────────
    Optional<Notification> findByIdempotencyKey(String idempotencyKey);

    boolean existsByIdempotencyKey(String idempotencyKey);

    // ── User-scoped queries ───────────────────────────────────────────────────
    Page<Notification> findByRecipientUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    Page<Notification> findByRecipientUserIdAndTypeOrderByCreatedAtDesc(
            String userId, NotificationType type, Pageable pageable);

    Page<Notification> findByRecipientUserIdAndStatusOrderByCreatedAtDesc(
            String userId, NotificationStatus status, Pageable pageable);

    long countByRecipientUserIdAndStatus(String userId, NotificationStatus status);

    // ── Admin / ops queries ──────────────────────────────────────────────────
    Page<Notification> findByStatusOrderByCreatedAtDesc(NotificationStatus status, Pageable pageable);

    Page<Notification> findByTypeOrderByCreatedAtDesc(NotificationType type, Pageable pageable);

    @Query("""
        SELECT n FROM Notification n
        WHERE n.status IN (:statuses)
          AND n.retryCount < n.maxRetries
          AND n.createdAt >= :since
        ORDER BY n.createdAt ASC
        """)
    List<Notification> findRetryableNotifications(
            @Param("statuses") List<NotificationStatus> statuses,
            @Param("since") LocalDateTime since
    );

    @Query("""
        SELECT n FROM Notification n
        WHERE n.status = 'PENDING'
          AND n.createdAt < :cutoff
        ORDER BY n.createdAt ASC
        """)
    List<Notification> findStalePendingNotifications(@Param("cutoff") LocalDateTime cutoff);

    // ── Reference-scoped queries (blog post, repo, comment) ──────────────────
    Page<Notification> findByReferenceIdAndReferenceTypeOrderByCreatedAtDesc(
            String referenceId, String referenceType, Pageable pageable);

    // ── Stats ────────────────────────────────────────────────────────────────
    @Query("""
        SELECT n.type, n.status, COUNT(n)
        FROM Notification n
        WHERE n.createdAt BETWEEN :from AND :to
        GROUP BY n.type, n.status
        """)
    List<Object[]> getNotificationStatsByPeriod(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.recipientUserId = :userId AND n.createdAt >= :since")
    long countRecentByUser(@Param("userId") String userId, @Param("since") LocalDateTime since);

    // ── Bulk status update ───────────────────────────────────────────────────
    @Modifying
    @Query("UPDATE Notification n SET n.status = :status, n.updatedAt = :now WHERE n.id IN :ids")
    int bulkUpdateStatus(
            @Param("ids") List<UUID> ids,
            @Param("status") NotificationStatus status,
            @Param("now") LocalDateTime now
    );

    // ── Cleanup ──────────────────────────────────────────────────────────────
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.status = 'SENT' AND n.sentAt < :cutoff")
    int deleteOldSentNotifications(@Param("cutoff") LocalDateTime cutoff);
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\NotificationServiceApplication.java`

```
package com.final_project.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\UserRegisteredConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.UserRegisteredEvent;
import com.final_project.notification_service.service.IdempotencyService;
import com.final_project.notification_service.service.strategy.UserRegisteredProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredConsumer {

    private final UserRegisteredProcessor processor;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics      = "${app.kafka.topics.user-registered}",
            groupId     = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, Object> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Received USER_REGISTERED event. Topic={} Partition={} Offset={} Key={}",
                topic, partition, offset, record.key());
        try {
            UserRegisteredEvent event = objectMapper.convertValue(record.value(), UserRegisteredEvent.class);

            String idempotencyKey = "consumer:user-registered:" + event.getEventId();
            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate USER_REGISTERED event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }

            processor.process(event);
            ack.acknowledge();
            log.info("USER_REGISTERED event processed successfully. EventId={}", event.getEventId());

        } catch (Exception ex) {
            log.error("Failed to process USER_REGISTERED event. Key={} Error={}",
                    record.key(), ex.getMessage(), ex);
            // Don't ack — let the error handler apply backoff / DLQ logic
            throw new RuntimeException("Failed to process USER_REGISTERED event", ex);
        }
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\RepositoryOperationConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.RepositoryOperationEvent;
import com.final_project.notification_service.service.IdempotencyService;
import com.final_project.notification_service.service.strategy.RepositoryOperationProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RepositoryOperationConsumer {

    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;
    private final RepositoryOperationProcessor repositoryOperationProcessor;

    @KafkaListener(
            topics = "${app.kafka.topics.repository.operation}",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, Object> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ) {
        log.info("Received REPOSITORY event. Topic={} Partition={} Offset={}",
                topic, partition, offset);

        try {
            RepositoryOperationEvent event =
                    objectMapper.convertValue(record.value(), RepositoryOperationEvent.class);

            String idempotencyKey = "consumer:repository-operation:" + event.getEventId();

            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate REPOSITORY event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }

            repositoryOperationProcessor.process(event);

            ack.acknowledge();

        } catch (Exception ex) {
            log.error("Failed to process REPOSITORY event. Key={} Error={}",
                    record.key(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process REPOSITORY event", ex);
        }
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\PasswordChangedConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.PasswordChangedEvent;
import com.final_project.notification_service.service.IdempotencyService;
import com.final_project.notification_service.service.strategy.PasswordChangedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordChangedConsumer {

    private final PasswordChangedProcessor processor;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics           = "${app.kafka.topics.password-changed}",
            groupId          = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, Object> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Received PASSWORD_CHANGED event. Topic={} Partition={} Offset={}", topic, partition, offset);
        try {
            PasswordChangedEvent event = objectMapper.convertValue(record.value(), PasswordChangedEvent.class);

            String idempotencyKey = "consumer:password-changed:" + event.getEventId();
            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate PASSWORD_CHANGED event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }

            processor.process(event);
            ack.acknowledge();

        } catch (Exception ex) {
            log.error("Failed to process PASSWORD_CHANGED event. Key={} Error={}", record.key(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process PASSWORD_CHANGED event", ex);
        }
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\InvitationSentConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.InvitationSentEvent;
import com.final_project.notification_service.service.IdempotencyService;
import com.final_project.notification_service.service.strategy.InvitationSentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvitationSentConsumer {

    private final InvitationSentProcessor processor;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics           = "${app.kafka.topics.invitation-sent}",
            groupId          = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, Object> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Received INVITATION_SENT event. Topic={} Partition={} Offset={}", topic, partition, offset);
        try {
            InvitationSentEvent event = objectMapper.convertValue(record.value(), InvitationSentEvent.class);

            String idempotencyKey = "consumer:invitation:" + event.getEventId();
            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate INVITATION_SENT event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }

            processor.process(event);
            ack.acknowledge();

        } catch (Exception ex) {
            log.error("Failed to process INVITATION_SENT event. Key={} Error={}", record.key(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process INVITATION_SENT event", ex);
        }
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\CommentRepliedConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.CommentRepliedEvent;
import com.final_project.notification_service.service.IdempotencyService;
import com.final_project.notification_service.service.strategy.CommentRepliedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentRepliedConsumer {

    private final CommentRepliedProcessor processor;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics           = "${app.kafka.topics.comment-replied}",
            groupId          = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, Object> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Received COMMENT_REPLIED event. Topic={} Partition={} Offset={}", topic, partition, offset);
        try {
            CommentRepliedEvent event = objectMapper.convertValue(record.value(), CommentRepliedEvent.class);

            String idempotencyKey = "consumer:comment-reply:" + event.getEventId();
            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate COMMENT_REPLIED event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }

            processor.process(event);
            ack.acknowledge();

        } catch (Exception ex) {
            log.error("Failed to process COMMENT_REPLIED event. Key={} Error={}", record.key(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process COMMENT_REPLIED event", ex);
        }
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\BlogInteractionConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.BlogInteractionEvent;
import com.final_project.notification_service.service.IdempotencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class BlogInteractionConsumer {
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${app.kafka.topics.article-operation}",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )public void consume(
            ConsumerRecord<String, Object>  record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ){

        log.info("Received BLOG_COMMENTED event. Topic={} Partition={} Offset={}", topic, partition, offset);
        try {
            BlogInteractionEvent event = objectMapper.convertValue(record.value(), BlogInteractionEvent.class);
            String idempotencyKey = "consumer:blog-comment:" + event.getEventId();
            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate BLOG_COMMENTED event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }
            ack.acknowledge();
        }catch (Exception ex){
            log.error("Failed to process BLOG_COMMENTED event. Key={} Error={}", record.key(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process BLOG_COMMENTED event", ex);
        }



    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\kafka\consumer\BlogCommentedConsumer.java`

```
package com.final_project.notification_service.kafka.consumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.event.BlogCommentedEvent;
import com.final_project.notification_service.service.IdempotencyService;
import com.final_project.notification_service.service.strategy.BlogCommentedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogCommentedConsumer {

    private final BlogCommentedProcessor processor;
    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics           = "${app.kafka.topics.blog-commented}",
            groupId          = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            ConsumerRecord<String, Object> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Received BLOG_COMMENTED event. Topic={} Partition={} Offset={}", topic, partition, offset);
        try {
            BlogCommentedEvent event = objectMapper.convertValue(record.value(), BlogCommentedEvent.class);

            String idempotencyKey = "consumer:blog-comment:" + event.getEventId();
            if (!idempotencyService.tryAcquire(idempotencyKey)) {
                log.warn("Duplicate BLOG_COMMENTED event skipped. EventId={}", event.getEventId());
                ack.acknowledge();
                return;
            }

            processor.process(event);
            ack.acknowledge();

        } catch (Exception ex) {
            log.error("Failed to process BLOG_COMMENTED event. Key={} Error={}", record.key(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process BLOG_COMMENTED event", ex);
        }
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\BlogInteractionProcessor.java`

```
package com.final_project.notification_service.service.strategy;
import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.BlogInteractionEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogInteractionProcessor implements NotificationProcessor<BlogInteractionEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(BlogInteractionEvent event) {

        if (isSelfAction(event)) {
            log.debug("Skipping self notification for userId={}", event.getActorUserId());
            return;
        }

        switch (event.getEventType()) {
            case COMMENT_REPLIED -> handleCommentReplied(event);

            case COMMENT_CREATED -> processBlogCommented(event);

            case ARTICLE_LIKED -> processBlogLiked(event);

            case ARTICLE_SHARED -> processBlogShared(event);

            case ARTICLE_PUBLISHED -> processBlogPublished(event);
            case ARTICLE_UNLIKED, COMMENT_DELETED -> {
                log.debug("No notification needed for eventType={}", event.getEventType());
            }
            default -> log.warn("Unhandled eventType={}", event.getEventType());
        }
    }

    private void handleCommentReplied(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_COMMENT_REPLY,
                event.getActorName() + " replied to your comment on: " + event.getBlogPostTitle(),
                event.getCommentSnippet(),
                event.getCommentId(),
                "COMMENT"
        );
    }

    private void processBlogCommented(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_NEW_COMMENT,
                event.getActorName() + " commented on your post: " + event.getBlogPostTitle(),
                "Blog comment notification sent.",
                event.getCommentId(),
                "COMMENT"
        );
    }

    private void processBlogLiked(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_POST_LIKED,
                event.getActorName() + " liked your post: " + event.getBlogPostTitle(),
                "Blog like notification sent.",
                event.getBlogPostId(),
                "BLOG_POST"
        );
    }

    private void processBlogShared(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_POST_SHARED,
                event.getActorName() + " shared your post: " + event.getBlogPostTitle(),
                "Blog share notification sent.",
                event.getBlogPostId(),
                "BLOG_POST"
        );
    }

    private void processBlogPublished(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_POST_PUBLISHED,
                "Your blog post has been published: " + event.getBlogPostTitle(),
                "Blog published notification sent.",
                event.getBlogPostId(),
                "BLOG_POST"
        );
    }

    private void sendArticleNotification(
            BlogInteractionEvent event,
            NotificationType notificationType,
            String subject,
            String notificationBody,
            String referenceId,
            String referenceType
    ) {
        emailService.sendHtmlEmail(
                event.getAuthorEmail(),
                subject,
                "article-notification",
                buildArticleTemplateModel(event)
        );

        saveNotification(
                event,
                notificationType,
                subject,
                notificationBody,
                referenceId,
                referenceType
        );
    }

    private Map<String, Object> buildArticleTemplateModel(BlogInteractionEvent event) {
        String headerTitle;
        String headerSubtitle;
        String mainMessage;
        String actionText;
        String messageBoxTitle;

        switch (event.getEventType()) {
            case COMMENT_CREATED -> {
                headerTitle = "New Comment";
                headerSubtitle = "Someone commented on your article";
                mainMessage = safe(event.getActorName()) + " commented on your article.";
                actionText = "View Comment";
                messageBoxTitle = "Comment";
            }
            case COMMENT_REPLIED -> {
                headerTitle = "New Reply";
                headerSubtitle = "Someone replied to your comment";
                mainMessage = safe(event.getActorName()) + " replied to your comment.";
                actionText = "View Reply";
                messageBoxTitle = "Reply";
            }
            case ARTICLE_LIKED -> {
                headerTitle = "Article Liked";
                headerSubtitle = "Someone liked your article";
                mainMessage = safe(event.getActorName()) + " liked your article.";
                actionText = "Open Article";
                messageBoxTitle = "Activity";
            }
            case ARTICLE_SHARED -> {
                headerTitle = "Article Shared";
                headerSubtitle = "Someone shared your article";
                mainMessage = safe(event.getActorName()) + " shared your article"
                        + (event.getSharePlatform() != null ? " on " + event.getSharePlatform() : "")
                        + ".";
                actionText = "Open Article";
                messageBoxTitle = "Share";
            }
            case ARTICLE_PUBLISHED -> {
                headerTitle = "Article Published";
                headerSubtitle = "Your article is now public";
                mainMessage = "Your article was approved and published.";
                actionText = "View Published Article";
                messageBoxTitle = "Publication";
            }
            default -> {
                headerTitle = "Article Update";
                headerSubtitle = "There is new activity on your article";
                mainMessage = "There is a new update related to your article.";
                actionText = "Open Article";
                messageBoxTitle = "Details";
            }
        }

        return Map.ofEntries(
                Map.entry("emailTitle", headerTitle),
                Map.entry("eventType", event.getEventType().name()),
                Map.entry("headerTitle", headerTitle),
                Map.entry("headerSubtitle", headerSubtitle),
                Map.entry("recipientName", safe(event.getAuthorName())),
                Map.entry("mainMessage", mainMessage),
                Map.entry("postTitle", safe(event.getBlogPostTitle())),
                Map.entry("postUrl", safe(event.getBlogPostUrl())),
                Map.entry("actionUrl", safe(event.getBlogPostUrl())),
                Map.entry("actionText", actionText),
                Map.entry("actorName", safe(event.getActorName())),
                Map.entry("actorEmail", safe(event.getActorEmail())),
                Map.entry("commentSnippet", truncate(event.getCommentSnippet(), 200)),
                Map.entry("messageBoxTitle", messageBoxTitle),
                Map.entry("sharePlatform", safe(event.getSharePlatform())),
                Map.entry("adminName", safe(event.getAdminName())),
                Map.entry("profile", safe(event.getProfile())),
                Map.entry("occurredAt", event.getOccurredAt() == null ? "" : event.getOccurredAt().toString()),
                Map.entry("baseUrl", props.getNotification().getBaseUrl())
        );
    }

    private void saveNotification(
            BlogInteractionEvent event,
            NotificationType type,
            String subject,
            String body,
            String referenceId,
            String referenceType
    ) {
        Notification notification = Notification.builder()
                .recipientUserId(event.getAuthorUserId())
                .recipientEmail(event.getAuthorEmail())
                .recipientName(event.getAuthorName())
                .type(type)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body(body)
                .referenceId(referenceId)
                .referenceType(referenceType)
                .idempotencyKey(event.getEventType() + ":" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    private boolean isSelfAction(BlogInteractionEvent event) {
        return event.getAuthorUserId() != null
                && event.getAuthorUserId().equals(event.getActorUserId());
    }

    @Override
    public Class<BlogInteractionEvent> supportedEventType() {
        return BlogInteractionEvent.class;
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max) + "…";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\BlogCommentedProcessor.java`

```
package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.BlogCommentedEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogCommentedProcessor implements NotificationProcessor<BlogCommentedEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(BlogCommentedEvent event) {
        // Don't notify the author if they commented on their own post
        if (event.getAuthorUserId().equals(event.getCommenterUserId())) {
            log.debug("Skipping self-comment notification for userId={}", event.getAuthorUserId());
            return;
        }

        log.info("Processing BLOG_NEW_COMMENT postId={} author={}",
                event.getBlogPostId(), event.getAuthorUserId());

        String subject = event.getCommenterName() + " commented on your post: " + event.getBlogPostTitle();

        emailService.sendHtmlEmail(
                event.getAuthorEmail(),
                subject,
                "blog-new-comment",
                Map.of(
                        "authorName",       event.getAuthorName(),
                        "commenterName",    event.getCommenterName(),
                        "postTitle",        event.getBlogPostTitle(),
                        "postUrl",          event.getBlogPostUrl(),
                        "commentSnippet",   truncate(event.getCommentSnippet(), 200),
                        "baseUrl",          props.getNotification().getBaseUrl()
                )
        );

        Notification notification = Notification.builder()
                .recipientUserId(event.getAuthorUserId())
                .recipientEmail(event.getAuthorEmail())
                .recipientName(event.getAuthorName())
                .type(NotificationType.BLOG_NEW_COMMENT)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body("New comment notification sent.")
                .referenceId(event.getBlogPostId())
                .referenceType("BLOG_POST")
                .idempotencyKey("blog-comment:" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    @Override
    public Class<BlogCommentedEvent> supportedEventType() {
        return BlogCommentedEvent.class;
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max) + "…";
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\RateLimitService.java`

```
package com.final_project.notification_service.service;

import com.final_project.notification_service.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Sliding-window rate limiter that prevents notification spam per user.
 * Uses Redis INCR + EXPIRE to implement a fixed 1-hour window counter.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RateLimitService {

    private static final String KEY_PREFIX = "notif:ratelimit:";

    private final RedisTemplate<String, String> redisTemplate;
    private final AppProperties appProperties;

    /**
     * @return {@code true} if the user is within their hourly notification quota.
     */
    public boolean isAllowed(String userId) {
        int max    = appProperties.getNotification().getRateLimit().getMaxPerUserPerHour();
        String key = KEY_PREFIX + userId;

        Long current = redisTemplate.opsForValue().increment(key);
        if (current == null) return true;

        if (current == 1L) {
            // First hit in the window — set TTL for 1 hour
            redisTemplate.expire(key, Duration.ofHours(1));
        }

        if (current > max) {
            log.warn("Rate limit exceeded for user={}. Count={} Max={}", userId, current, max);
            return false;
        }

        return true;
    }

    /**
     * Returns how many notifications the user has sent in the current window.
     */
    public long currentCount(String userId) {
        String value = redisTemplate.opsForValue().get(KEY_PREFIX + userId);
        return value != null ? Long.parseLong(value) : 0L;
    }

    /**
     * Resets the counter for a user (admin use).
     */
    public void reset(String userId) {
        redisTemplate.delete(KEY_PREFIX + userId);
        log.info("Rate limit counter reset for user={}", userId);
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\NotificationServiceImpl.java`

```
package com.final_project.notification_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.dto.mapper.NotificationMapper;
import com.final_project.notification_service.dto.request.ResendNotificationRequest;
import com.final_project.notification_service.dto.request.SendNotificationRequest;
import com.final_project.notification_service.dto.response.NotificationResponse;
import com.final_project.notification_service.dto.response.NotificationStatsResponse;
import com.final_project.notification_service.dto.response.PagedResponse;
import com.final_project.notification_service.exception.DuplicateNotificationException;
import com.final_project.notification_service.exception.NotificationNotFoundException;
import com.final_project.notification_service.exception.RateLimitExceededException;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.repo.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;
    private final EmailService emailService;
    private final RateLimitService rateLimitService;

    // ── Sending ──────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public NotificationResponse send(SendNotificationRequest req) {
        String idempotencyKey = resolveIdempotencyKey(req);

        // 1. Idempotency check
        if (repository.existsByIdempotencyKey(idempotencyKey)) {
            log.warn("Duplicate notification request. Key={}", idempotencyKey);
            throw new DuplicateNotificationException(
                    "Notification with this idempotency key already exists: " + idempotencyKey);
        }

        // 2. Rate limit check
        if (!rateLimitService.isAllowed(req.getRecipientUserId())) {
            throw new RateLimitExceededException(
                    "Notification rate limit exceeded for user: " + req.getRecipientUserId());
        }

        // 3. Persist
        Notification notification = Notification.builder()
                .recipientUserId(req.getRecipientUserId())
                .recipientEmail(req.getRecipientEmail())
                .recipientName(req.getRecipientName())
                .type(req.getType())
                .channel(req.getChannel() != null ? req.getChannel() : NotificationChannel.EMAIL)
                .status(NotificationStatus.PENDING)
                .subject(req.getSubject())
                .body(req.getBody())
                .referenceId(req.getReferenceId())
                .referenceType(req.getReferenceType())
                .idempotencyKey(idempotencyKey)
                .build();

        return mapper.toResponse(saveAndProcess(notification));
    }

    @Override
    @Transactional
    public NotificationResponse resend(ResendNotificationRequest req) {
        Notification original = repository.findById(req.getNotificationId())
                .orElseThrow(() -> new NotificationNotFoundException(req.getNotificationId()));

        if (!original.canRetry()) {
            throw new IllegalStateException(
                    "Notification cannot be retried. Status=" + original.getStatus()
                            + " RetryCount=" + original.getRetryCount());
        }

        String targetEmail = req.getOverrideEmail() != null
                ? req.getOverrideEmail()
                : original.getRecipientEmail();

        original.incrementRetry();
        dispatchEmail(original, targetEmail);

        return mapper.toResponse(repository.save(original));
    }

    // ── Querying ─────────────────────────────────────────────────────────────

    @Override
    public NotificationResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }

    @Override
    public PagedResponse<NotificationResponse> findByUser(String userId, Pageable pageable) {
        Page<Notification> page = repository.findByRecipientUserIdOrderByCreatedAtDesc(userId, pageable);
        return PagedResponse.of(page.map(mapper::toResponse));
    }

    @Override
    public PagedResponse<NotificationResponse> findByUserAndType(
            String userId, NotificationType type, Pageable pageable
    ) {
        Page<Notification> page = repository
                .findByRecipientUserIdAndTypeOrderByCreatedAtDesc(userId, type, pageable);
        return PagedResponse.of(page.map(mapper::toResponse));
    }

    @Override
    public PagedResponse<NotificationResponse> findByUserAndStatus(
            String userId, NotificationStatus status, Pageable pageable
    ) {
        Page<Notification> page = repository
                .findByRecipientUserIdAndStatusOrderByCreatedAtDesc(userId, status, pageable);
        return PagedResponse.of(page.map(mapper::toResponse));
    }

    @Override
    public PagedResponse<NotificationResponse> findAll(Pageable pageable) {
        return PagedResponse.of(repository.findAll(pageable).map(mapper::toResponse));
    }

    @Override
    public PagedResponse<NotificationResponse> findByStatus(
            NotificationStatus status, Pageable pageable
    ) {
        return PagedResponse.of(
                repository.findByStatusOrderByCreatedAtDesc(status, pageable).map(mapper::toResponse));
    }

    @Override
    public PagedResponse<NotificationResponse> findByType(
            NotificationType type, Pageable pageable
    ) {
        return PagedResponse.of(
                repository.findByTypeOrderByCreatedAtDesc(type, pageable).map(mapper::toResponse));
    }

    @Override
    public PagedResponse<NotificationResponse> findByReference(
            String referenceId, String referenceType, Pageable pageable
    ) {
        Page<Notification> page = repository
                .findByReferenceIdAndReferenceTypeOrderByCreatedAtDesc(referenceId, referenceType, pageable);
        return PagedResponse.of(page.map(mapper::toResponse));
    }

    // ── Stats ────────────────────────────────────────────────────────────────

    @Override
    public NotificationStatsResponse getStats(LocalDateTime from, LocalDateTime to) {
        List<Object[]> raw = repository.getNotificationStatsByPeriod(from, to);

        Map<String, Long> byStatus = new LinkedHashMap<>();
        Map<String, Long> byType   = new LinkedHashMap<>();
        long total = 0;

        for (Object[] row : raw) {
            String type   = row[0].toString();
            String status = row[1].toString();
            long count    = ((Number) row[2]).longValue();
            total += count;
            byType.merge(type, count, Long::sum);
            byStatus.merge(status, count, Long::sum);
        }

        long sent   = byStatus.getOrDefault("SENT", 0L);
        double rate = total > 0 ? (sent * 100.0 / total) : 0.0;

        return NotificationStatsResponse.builder()
                .total(total)
                .byStatus(byStatus)
                .byType(byType)
                .successRate(Math.round(rate * 100.0) / 100.0)
                .averageRetries(0.0) // could be derived with an extra query
                .build();
    }

    @Override
    public long countUnreadByUser(String userId) {
        return repository.countByRecipientUserIdAndStatus(userId, NotificationStatus.PENDING);
    }

    // ── Admin / scheduled ops ────────────────────────────────────────────────

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "PT5M")
    public void retryFailedNotifications() {
        List<NotificationStatus> retryableStatuses =
                List.of(NotificationStatus.FAILED, NotificationStatus.RETRYING);

        LocalDateTime since = LocalDateTime.now().minusHours(24);
        List<Notification> candidates = repository.findRetryableNotifications(retryableStatuses, since);

        if (candidates.isEmpty()) {
            log.debug("No retryable notifications found.");
            return;
        }

        log.info("Retrying {} failed notifications.", candidates.size());
        for (Notification n : candidates) {
            try {
                n.incrementRetry();
                dispatchEmail(n, n.getRecipientEmail());
                repository.save(n);
            } catch (Exception ex) {
                log.error("Retry failed for notification id={}. Error={}", n.getId(), ex.getMessage());
                n.markFailed(ex.getMessage());
                repository.save(n);
            }
        }
    }

    @Override
    @Transactional
    public void deleteOldSentNotifications(LocalDateTime before) {
        int deleted = repository.deleteOldSentNotifications(before);
        log.info("Cleaned up {} old sent notifications older than {}", deleted, before);
    }

    // ── Internal ─────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public Notification saveAndProcess(Notification notification) {
        Notification saved = repository.save(notification);

        try {
            saved.setStatus(NotificationStatus.PROCESSING);
            repository.save(saved);

            dispatchEmail(saved, saved.getRecipientEmail());

            saved.markSent();
        } catch (Exception ex) {
            log.error("Failed to process notification id={}. Error={}", saved.getId(), ex.getMessage());
            saved.markFailed(ex.getMessage());
        }

        return repository.save(saved);
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private void dispatchEmail(Notification notification, String targetEmail) {
        emailService.sendPlainTextEmail(
                targetEmail,
                notification.getSubject(),
                notification.getBody()
        ).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Async email dispatch failed for notification id={}. Error={}",
                        notification.getId(), ex.getMessage());
            }
        });
    }

    private String resolveIdempotencyKey(SendNotificationRequest req) {
        if (req.getIdempotencyKey() != null && !req.getIdempotencyKey().isBlank()) {
            return req.getIdempotencyKey();
        }
        // Auto-generate deterministic key from user + type + current hour
        return String.format("%s:%s:%s",
                req.getRecipientUserId(),
                req.getType(),
                LocalDateTime.now().withMinute(0).withSecond(0).withNano(0)
        );
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\NotificationService.java`

```
package com.final_project.notification_service.service;


import com.final_project.notification_service.dto.request.ResendNotificationRequest;
import com.final_project.notification_service.dto.request.SendNotificationRequest;
import com.final_project.notification_service.dto.response.NotificationResponse;
import com.final_project.notification_service.dto.response.NotificationStatsResponse;
import com.final_project.notification_service.dto.response.PagedResponse;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationService {

    // ── Sending ──────────────────────────────────────────────────────────────

    NotificationResponse send(SendNotificationRequest request);

    NotificationResponse resend(ResendNotificationRequest request);

    // ── Querying ─────────────────────────────────────────────────────────────

    NotificationResponse findById(UUID id);

    PagedResponse<NotificationResponse> findByUser(String userId, Pageable pageable);

    PagedResponse<NotificationResponse> findByUserAndType(
            String userId, NotificationType type, Pageable pageable);

    PagedResponse<NotificationResponse> findByUserAndStatus(
            String userId, NotificationStatus status, Pageable pageable);

    PagedResponse<NotificationResponse> findAll(Pageable pageable);

    PagedResponse<NotificationResponse> findByStatus(NotificationStatus status, Pageable pageable);

    PagedResponse<NotificationResponse> findByType(NotificationType type, Pageable pageable);

    PagedResponse<NotificationResponse> findByReference(
            String referenceId, String referenceType, Pageable pageable);

    // ── Stats ────────────────────────────────────────────────────────────────

    NotificationStatsResponse getStats(LocalDateTime from, LocalDateTime to);

    long countUnreadByUser(String userId);

    // ── Admin operations ─────────────────────────────────────────────────────

    void retryFailedNotifications();

    void deleteOldSentNotifications(LocalDateTime before);

    // ── Internal (used by processors) ────────────────────────────────────────

    Notification saveAndProcess(Notification notification);
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\IdempotencyService.java`

```
package com.final_project.notification_service.service;
import com.final_project.notification_service.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Provides Redis-backed idempotency checks so that duplicate Kafka events
 * (e.g. from consumer restarts or at-least-once delivery) never trigger
 * duplicate notifications.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IdempotencyService {

    private static final String KEY_PREFIX = "notif:idempotency:";

    private final RedisTemplate<String, String> redisTemplate;
    private final AppProperties appProperties;

    /**
     * Attempts to claim an idempotency key.
     *
     * @return {@code true} if this is the first time this key is seen (proceed);
     *         {@code false} if the key already exists (skip — duplicate).
     */
    public boolean tryAcquire(String idempotencyKey) {
        String redisKey = KEY_PREFIX + idempotencyKey;
        long ttlHours   = appProperties.getNotification().getIdempotencyTtlHours();

        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(redisKey, "1", Duration.ofHours(ttlHours));

        if (Boolean.TRUE.equals(acquired)) {
            log.debug("Idempotency key acquired. Key={}", idempotencyKey);
            return true;
        }

        log.warn("Duplicate event detected — skipping. Key={}", idempotencyKey);
        return false;
    }

    /**
     * Explicitly releases a key (e.g. after a processing failure so it can be retried).
     */
    public void release(String idempotencyKey) {
        String redisKey = KEY_PREFIX + idempotencyKey;
        redisTemplate.delete(redisKey);
        log.debug("Idempotency key released. Key={}", idempotencyKey);
    }

    /**
     * Checks whether a key exists without claiming it.
     */
    public boolean exists(String idempotencyKey) {
        return redisTemplate.hasKey(KEY_PREFIX + idempotencyKey);
    }

    /**
     * Returns remaining TTL in seconds, or -1 if not found.
     */
    public long remainingTtlSeconds(String idempotencyKey) {
        Long ttl = redisTemplate.getExpire(KEY_PREFIX + idempotencyKey, TimeUnit.SECONDS);
        return ttl != null ? ttl : -1L;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\NotificationProcessor.java`

```
package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.model.Notification;

/**
 * Strategy contract for building a {@link Notification} from a raw Kafka event payload.
 * Each event type gets its own implementation.
 */
public interface NotificationProcessor<T> {

    /**
     * Builds and persists the notification for the given event.
     *
     * @param event the deserialized Kafka event
     */
    void process(T event);

    /**
     * Returns the event class this processor handles (used for dispatcher registration).
     */
    Class<T> supportedEventType();
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\InvitationSentProcessor.java`

```
package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.InvitationSentEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvitationSentProcessor implements NotificationProcessor<InvitationSentEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(InvitationSentEvent event) {
        log.info("Processing INVITATION_SENT type={} invitee={}",
                event.getInvitationType(), event.getInviteeEmail());

        boolean isRepoInvite = "REPOSITORY".equals(event.getInvitationType());

        String subject = isRepoInvite
                ? event.getInviterName() + " invited you to collaborate on " + event.getTargetName()
                : event.getInviterName() + " invited you to join " + event.getTargetName();

        String acceptUrl = props.getNotification().getBaseUrl()
                + "/invitations/accept?token=" + event.getInvitationToken();

        String template = isRepoInvite ? "repo-invitation.html" : "system-invitation";

        emailService.sendHtmlEmail(
                event.getInviteeEmail(),
                subject,
                template,
                Map.of(
                        "inviteeName",    event.getInviteeName() != null ? event.getInviteeName() : "there",
                        "inviterName",    event.getInviterName(),
                        "targetName",     event.getTargetName(),
                        "acceptUrl",      acceptUrl,
                        "expiresAt",      event.getExpiresAt().toString(),
                        "baseUrl",        props.getNotification().getBaseUrl()
                )
        );

        NotificationType type = isRepoInvite
                ? NotificationType.REPOSITORY_INVITATION
                : NotificationType.SYSTEM_INVITATION;

        Notification notification = Notification.builder()
                .recipientUserId(event.getInviteeEmail())      // user may not exist yet
                .recipientEmail(event.getInviteeEmail())
                .recipientName(event.getInviteeName())
                .type(type)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body("Invitation email sent.")
                .referenceId(event.getTargetId())
                .referenceType(isRepoInvite ? "REPOSITORY" : "SYSTEM")
                .idempotencyKey("invitation:" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    @Override
    public Class<InvitationSentEvent> supportedEventType() {
        return InvitationSentEvent.class;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\CommentRepliedProcessor.java`

```
package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.CommentRepliedEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentRepliedProcessor implements NotificationProcessor<CommentRepliedEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(CommentRepliedEvent event) {
        // Don't notify if someone replied to their own comment
        if (event.getOriginalCommenterUserId().equals(event.getReplierUserId())) {
            log.debug("Skipping self-reply notification for userId={}", event.getReplierUserId());
            return;
        }

        log.info("Processing COMMENT_REPLY commentId={} replier={}",
                event.getParentCommentId(), event.getReplierUserId());

        String subject = event.getReplierName() + " replied to your comment on: " + event.getBlogPostTitle();

        emailService.sendHtmlEmail(
                event.getOriginalCommenterEmail(),
                subject,
                "comment-replied",
                Map.of(
                        "commenterName",    event.getOriginalCommenterName(),
                        "replierName",      event.getReplierName(),
                        "postTitle",        event.getBlogPostTitle(),
                        "postUrl",          event.getBlogPostUrl(),
                        "replySnippet",     truncate(event.getReplySnippet(), 200),
                        "baseUrl",          props.getNotification().getBaseUrl()
                )
        );

        Notification notification = Notification.builder()
                .recipientUserId(event.getOriginalCommenterUserId())
                .recipientEmail(event.getOriginalCommenterEmail())
                .recipientName(event.getOriginalCommenterName())
                .type(NotificationType.BLOG_COMMENT_REPLY)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body("Comment reply notification sent.")
                .referenceId(event.getParentCommentId())
                .referenceType("COMMENT")
                .idempotencyKey("comment-reply:" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    @Override
    public Class<CommentRepliedEvent> supportedEventType() {
        return CommentRepliedEvent.class;
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max) + "…";
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\RepositoryOperationProcessor.java`

```
package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.RepositoryOperationEvent;
import com.final_project.notification_service.model.*;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RepositoryOperationProcessor implements NotificationProcessor<RepositoryOperationEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(RepositoryOperationEvent event) {
        switch (event.getEventType()) {

            case REPOSITORY_PUSHED, COMMIT_CREATED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_PUSH,
                    safe(event.getActorName()) + " pushed changes to " + safe(event.getRepositoryName()),
                    "Repository push notification sent."
            );

            case REPOSITORY_INVITATION_SENT -> notifyInvitedUser(event);

            case REPOSITORY_INVITATION_ACCEPTED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_INVITATION_ACCEPTED,
                    safe(event.getInvitedUserName()) + " accepted repository invitation",
                    "Repository invitation accepted notification sent."
            );

            case BRANCH_CREATED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_BRANCH_CREATED,
                    safe(event.getActorName()) + " created branch " + safe(event.getBranchName()),
                    "Repository branch created notification sent."
            );

            case BRANCH_MERGED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_BRANCH_MERGED,
                    safe(event.getActorName()) + " merged " + safe(event.getSourceBranch()) + " into " + safe(event.getTargetBranch()),
                    "Repository branch merged notification sent."
            );

            case PULL_REQUEST_OPENED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_PULL_REQUEST_OPENED,
                    safe(event.getActorName()) + " opened a pull request in " + safe(event.getRepositoryName()),
                    "Pull request opened notification sent."
            );

            case PULL_REQUEST_MERGED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_PULL_REQUEST_MERGED,
                    safe(event.getActorName()) + " merged a pull request in " + safe(event.getRepositoryName()),
                    "Pull request merged notification sent."
            );

            case REPOSITORY_PULLED, REPOSITORY_FETCHED, REPOSITORY_CLONED -> {
                log.debug("No notification needed for eventType={}", event.getEventType());
            }

            default -> log.warn("Unhandled repository eventType={}", event.getEventType());
        }
    }

    private void notifyRepositoryMembers(
            RepositoryOperationEvent event,
            NotificationType notificationType,
            String subject,
            String body
    ) {
        List<RepositoryMemberRecipient> recipients = event.getRecipients();

        if (recipients == null || recipients.isEmpty()) {
            log.warn("No recipients found for repository eventId={}", event.getEventId());
            return;
        }

        for (RepositoryMemberRecipient recipient : recipients) {

            if (isActor(event, recipient)) {
                log.debug("Skipping actor notification for userId={}", recipient.getUserId());
                continue;
            }

            emailService.sendHtmlEmail(
                    recipient.getEmail(),
                    subject,
                    "repository-notification",
                    buildRepositoryTemplateModel(event, recipient)
            );

            saveNotification(
                    event,
                    recipient,
                    notificationType,
                    subject,
                    body,
                    event.getRepositoryId(),
                    "REPOSITORY"
            );
        }
    }

    private void notifyInvitedUser(RepositoryOperationEvent event) {
        if (event.getInvitedUserId() != null &&
                event.getInvitedUserId().equals(event.getActorUserId())) {
            log.debug("Skipping self invitation notification userId={}", event.getActorUserId());
            return;
        }

        String subject = safe(event.getActorName())
                + " invited you to repository "
                + safe(event.getRepositoryName());

        RepositoryMemberRecipient recipient = RepositoryMemberRecipient.builder()
                .userId(event.getInvitedUserId())
                .name(event.getInvitedUserName())
                .email(event.getInvitedUserEmail())
                .role("INVITED")
                .build();

        emailService.sendHtmlEmail(
                event.getInvitedUserEmail(),
                subject,
                "repository-notification",
                buildRepositoryTemplateModel(event, recipient)
        );

        saveNotification(
                event,
                recipient,
                NotificationType.REPOSITORY_INVITATION_SENT,
                subject,
                "Repository invitation notification sent.",
                event.getRepositoryId(),
                "REPOSITORY"
        );
    }

    private Map<String, Object> buildRepositoryTemplateModel(
            RepositoryOperationEvent event,
            RepositoryMemberRecipient recipient
    ) {
        String headerTitle;
        String headerSubtitle;
        String mainMessage;
        String actionText;

        switch (event.getEventType()) {
            case REPOSITORY_PUSHED, COMMIT_CREATED -> {
                headerTitle = "New Repository Push";
                headerSubtitle = "New changes were pushed";
                mainMessage = safe(event.getActorName()) + " pushed "
                        + (event.getCommitCount() == null ? "new changes" : event.getCommitCount() + " commit(s)")
                        + " to " + safe(event.getRepositoryName()) + ".";
                actionText = "View Changes";
            }
            case REPOSITORY_INVITATION_SENT -> {
                headerTitle = "Repository Invitation";
                headerSubtitle = "You were invited to a repository";
                mainMessage = safe(event.getActorName()) + " invited you to join " + safe(event.getRepositoryName()) + ".";
                actionText = "Open Invitation";
            }
            case REPOSITORY_INVITATION_ACCEPTED -> {
                headerTitle = "Invitation Accepted";
                headerSubtitle = "A member joined the repository";
                mainMessage = safe(event.getInvitedUserName()) + " accepted the invitation to "
                        + safe(event.getRepositoryName()) + ".";
                actionText = "Open Repository";
            }
            case BRANCH_CREATED -> {
                headerTitle = "Branch Created";
                headerSubtitle = "A new branch was created";
                mainMessage = safe(event.getActorName()) + " created branch " + safe(event.getBranchName()) + ".";
                actionText = "Open Repository";
            }
            case BRANCH_MERGED -> {
                headerTitle = "Branch Merged";
                headerSubtitle = "A branch was merged";
                mainMessage = safe(event.getActorName()) + " merged "
                        + safe(event.getSourceBranch()) + " into " + safe(event.getTargetBranch()) + ".";
                actionText = "View Merge";
            }
            case PULL_REQUEST_OPENED -> {
                headerTitle = "Pull Request Opened";
                headerSubtitle = "A new pull request was opened";
                mainMessage = safe(event.getActorName()) + " opened a pull request: "
                        + safe(event.getPullRequestTitle());
                actionText = "Open Pull Request";
            }
            case PULL_REQUEST_MERGED -> {
                headerTitle = "Pull Request Merged";
                headerSubtitle = "A pull request was merged";
                mainMessage = safe(event.getActorName()) + " merged a pull request: "
                        + safe(event.getPullRequestTitle());
                actionText = "View Pull Request";
            }
            default -> {
                headerTitle = "Repository Update";
                headerSubtitle = "There is new repository activity";
                mainMessage = "There is a new update in " + safe(event.getRepositoryName()) + ".";
                actionText = "Open Repository";
            }
        }

        String actionUrl = event.getPullRequestUrl() != null && !event.getPullRequestUrl().isBlank()
                ? event.getPullRequestUrl()
                : event.getRepositoryUrl();

        return Map.ofEntries(
                Map.entry("emailTitle", headerTitle),
                Map.entry("eventType", event.getEventType().name()),
                Map.entry("headerTitle", headerTitle),
                Map.entry("headerSubtitle", headerSubtitle),
                Map.entry("recipientName", safe(recipient.getName())),
                Map.entry("mainMessage", mainMessage),
                Map.entry("repositoryName", safe(event.getRepositoryName())),
                Map.entry("repositoryUrl", safe(event.getRepositoryUrl())),
                Map.entry("actionUrl", safe(actionUrl)),
                Map.entry("actionText", actionText),
                Map.entry("actorName", safe(event.getActorName())),
                Map.entry("actorEmail", safe(event.getActorEmail())),
                Map.entry("branchName", safe(event.getBranchName())),
                Map.entry("sourceBranch", safe(event.getSourceBranch())),
                Map.entry("targetBranch", safe(event.getTargetBranch())),
                Map.entry("commitId", safe(event.getCommitId())),
                Map.entry("commitMessage", safe(event.getCommitMessage())),
                Map.entry("commitCount", event.getCommitCount() == null ? "" : event.getCommitCount()),
                Map.entry("pullRequestTitle", safe(event.getPullRequestTitle())),
                Map.entry("pullRequestUrl", safe(event.getPullRequestUrl())),
                Map.entry("occurredAt", event.getOccurredAt() == null ? "" : event.getOccurredAt().toString()),
                Map.entry("baseUrl", props.getNotification().getBaseUrl())
        );
    }

    private void saveNotification(
            RepositoryOperationEvent event,
            RepositoryMemberRecipient recipient,
            NotificationType type,
            String subject,
            String body,
            String referenceId,
            String referenceType
    ) {
        Notification notification = Notification.builder()
                .recipientUserId(recipient.getUserId())
                .recipientEmail(recipient.getEmail())
                .recipientName(recipient.getName())
                .type(type)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body(body)
                .referenceId(referenceId)
                .referenceType(referenceType)
                .idempotencyKey(event.getEventType() + ":" + event.getEventId() + ":" + recipient.getUserId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    private boolean isActor(RepositoryOperationEvent event, RepositoryMemberRecipient recipient) {
        return recipient.getUserId() != null
                && recipient.getUserId().equals(event.getActorUserId());
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    @Override
    public Class<RepositoryOperationEvent> supportedEventType() {
        return RepositoryOperationEvent.class;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\PasswordChangedProcessor.java`

```
package com.final_project.notification_service.service.strategy;
import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.PasswordChangedEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordChangedProcessor implements NotificationProcessor<PasswordChangedEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(PasswordChangedEvent event) {
        log.info("Processing PASSWORD_CHANGED for userId={} changeType={}",
                event.getUserId(), event.getChangeType());

        String subject = "RESET".equals(event.getChangeType())
                ? "Your password has been reset"
                : "Your password was changed";

        String securityUrl = props.getNotification().getBaseUrl() + "/security";

        emailService.sendHtmlEmail(
                event.getEmail(),
                subject,
                "password-changed",
                Map.of(
                        "firstName",    event.getFirstName(),
                        "changeType",   event.getChangeType(),
                        "ipAddress",    event.getIpAddress() != null ? event.getIpAddress() : "unknown",
                        "occurredAt",   event.getOccurredAt().toString(),
                        "securityUrl",  securityUrl,
                        "baseUrl",      props.getNotification().getBaseUrl()
                )
        );

        Notification notification = Notification.builder()
                .recipientUserId(event.getUserId())
                .recipientEmail(event.getEmail())
                .recipientName(event.getFirstName())
                .type(NotificationType.PASSWORD_CHANGED)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body("Password " + event.getChangeType().toLowerCase() + " security alert sent.")
                .idempotencyKey("password-changed:" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    @Override
    public Class<PasswordChangedEvent> supportedEventType() {
        return PasswordChangedEvent.class;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\service\strategy\UserRegisteredProcessor.java`

```
package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.UserRegisteredEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredProcessor implements NotificationProcessor<UserRegisteredEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(UserRegisteredEvent event) {
        log.info("Processing USER_REGISTERED for userId={}", event.getUserId());

        String fullName  = event.getFirstName() + " " + event.getLastName();
        String subject   = "Welcome to the platform, " + event.getFirstName() + "!";
        String verifyUrl = props.getNotification().getBaseUrl()
                + "/verify-email?token=" + event.getVerificationToken();

        // Send rich HTML email via template
        emailService.sendHtmlEmail(
                event.getEmail(),
                subject,
                "user-registered",
                Map.of(
                        "firstName",        event.getFirstName(),
                        "fullName",         fullName,
                        "verificationUrl",  verifyUrl,
                        "baseUrl",          props.getNotification().getBaseUrl()
                )
        );

        // Persist notification record
        Notification notification = Notification.builder()
                .recipientUserId(event.getUserId())
                .recipientEmail(event.getEmail())
                .recipientName(fullName)
                .type(NotificationType.USER_REGISTERED)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body("Welcome email sent with verification link.")
                .idempotencyKey("user-registered:" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    @Override
    public Class<UserRegisteredEvent> supportedEventType() {
        return UserRegisteredEvent.class;
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\request\SendNotificationRequest.java`

```
package com.final_project.notification_service.dto.request;

import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to send a custom notification to a user")
public class SendNotificationRequest {

    @NotBlank(message = "Recipient user ID is required")
    @Schema(description = "Target user identifier", example = "usr_abc123")
    private String recipientUserId;

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Must be a valid email address")
    @Size(max = 320)
    @Schema(description = "Target email address", example = "john@example.com")
    private String recipientEmail;

    @Size(max = 200)
    @Schema(description = "Display name of recipient", example = "John Doe")
    private String recipientName;

    @NotNull(message = "Notification type is required")
    @Schema(description = "Type of notification")
    private NotificationType type;

    @NotNull(message = "Channel is required")
    @Builder.Default
    @Schema(description = "Delivery channel", defaultValue = "EMAIL")
    private NotificationChannel channel = NotificationChannel.EMAIL;

    @NotBlank(message = "Subject is required")
    @Size(max = 500, message = "Subject must not exceed 500 characters")
    @Schema(description = "Email subject line", example = "Welcome to our platform!")
    private String subject;

    @NotBlank(message = "Body is required")
    @Schema(description = "Notification body (HTML supported for email)")
    private String body;

    @Size(max = 100)
    @Schema(description = "Reference entity ID (blog post, repo, comment)", example = "post_xyz")
    private String referenceId;

    @Size(max = 50)
    @Schema(description = "Reference entity type", example = "BLOG_POST")
    private String referenceType;

    @Size(max = 200)
    @Schema(description = "Optional idempotency key for deduplication")
    private String idempotencyKey;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\request\ResendNotificationRequest.java`

```
package com.final_project.notification_service.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to resend a previously failed notification")
public class ResendNotificationRequest {

    @NotNull(message = "Notification ID is required")
    @Schema(description = "ID of the notification to resend")
    private UUID notificationId;

    @Schema(description = "Override recipient email (optional — uses original if omitted)")
    @Email
    private String overrideEmail;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\dto\mapper\NotificationMapper.java`

```
package com.final_project.notification_service.dto.mapper;
import com.final_project.notification_service.dto.response.NotificationResponse;
import com.final_project.notification_service.model.Notification;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponseList(List<Notification> notifications);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromNotification(Notification source, @MappingTarget Notification target);
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\NotificationStatus.java`

```
package com.final_project.notification_service.model;

public enum NotificationStatus {
    PENDING,
    PROCESSING,
    SENT,
    FAILED,
    RETRYING,
    SKIPPED     // idempotency hit — already processed
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\NotificationChannel.java`

```
package com.final_project.notification_service.model;
public enum NotificationChannel {
    SMS,
    EMAIL,
    PUSH,
    IN_APP
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\Notification.java`

```
package com.final_project.notification_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_notif_user_id",   columnList = "recipient_user_id"),
                @Index(name = "idx_notif_type",      columnList = "type"),
                @Index(name = "idx_notif_status",    columnList = "status"),
                @Index(name = "idx_notif_created",   columnList = "created_at"),
                @Index(name = "idx_notif_idempotency", columnList = "idempotency_key", unique = true)
        }
)
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "metadata")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "recipient_user_id", nullable = false)
    private String recipientUserId;

    @Column(name = "recipient_email", nullable = false, length = 320)
    private String recipientEmail;

    @Column(name = "recipient_name", length = 200)
    private String recipientName;

    // ── Classification ───────────────────────────────────────
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 20)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private NotificationStatus status = NotificationStatus.PENDING;

    // ── Content ──────────────────────────────────────────────
    @Column(name = "subject", nullable = false, length = 500)
    private String subject;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    // ── Reference context ────────────────────────────────────
    @Column(name = "reference_id", length = 100)
    private String referenceId;       // blogPostId, repoId, commentId, etc.

    @Column(name = "reference_type", length = 50)
    private String referenceType;     // BLOG_POST, REPOSITORY, COMMENT

    // ── Reliability ──────────────────────────────────────────
    @Column(name = "idempotency_key", nullable = false, unique = true, length = 200)
    private String idempotencyKey;

    @Column(name = "retry_count")
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "max_retries")
    @Builder.Default
    private Integer maxRetries = 3;

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    private String failureReason;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    // ── Metadata JSON (source event data snapshot) ───────────
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    // ── Audit ────────────────────────────────────────────────
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ── Helpers ──────────────────────────────────────────────
    public boolean canRetry() {
        return retryCount < maxRetries && status != NotificationStatus.SENT;
    }

    public void markSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void markFailed(String reason) {
        this.status = NotificationStatus.FAILED;
        this.failureReason = reason;
    }

    public void incrementRetry() {
        this.retryCount++;
        this.status = NotificationStatus.RETRYING;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\ArticleEventType.java`

```
package com.final_project.notification_service.model;

public enum ArticleEventType {
    ARTICLE_LIKED,
    ARTICLE_UNLIKED,
    ARTICLE_SHARED,
    COMMENT_CREATED,
    COMMENT_REPLIED,
    COMMENT_DELETED,
    ARTICLE_PUBLISHED
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\controller\NotificationController.java`

```
package com.final_project.notification_service.controller;


import com.final_project.notification_service.dto.request.ResendNotificationRequest;
import com.final_project.notification_service.dto.request.SendNotificationRequest;
import com.final_project.notification_service.dto.response.ApiResponse;
import com.final_project.notification_service.dto.response.NotificationResponse;
import com.final_project.notification_service.dto.response.NotificationStatsResponse;
import com.final_project.notification_service.dto.response.PagedResponse;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Notifications", description = "Notification management and delivery endpoints")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {
    private final NotificationService notificationService;


    @PostMapping
    @Operation(summary = "Send a custom notification",
            description = "Create and dispatch a one-off notification to a user via REST")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Notification sent"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate — already sent"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    public ResponseEntity<ApiResponse<NotificationResponse>> sendNotification(
            @Valid @RequestBody SendNotificationRequest request
    ) {
        log.info("REST request to send notification. UserId={} Type={}",
                request.getRecipientUserId(), request.getType());
        NotificationResponse response = notificationService.send(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Notification sent successfully"));
    }

    @PostMapping("/{id}/resend")
    @Operation(summary = "Resend a failed notification",
            description = "Retry sending a notification that previously failed")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notification resent"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Notification not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Cannot retry this notification")
    })
    public ResponseEntity<ApiResponse<NotificationResponse>> resendNotification(
            @PathVariable UUID id,
            @Valid @RequestBody ResendNotificationRequest request
    ) {
        log.info("REST request to resend notification. Id={}", id);
        request.setNotificationId(id);
        NotificationResponse response = notificationService.resend(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Notification resent"));
    }

    // ── Query single ──────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    @Operation(summary = "Get a notification by ID")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notification found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.findById(id)));
    }

    // ── Query by user ─────────────────────────────────────────────────────────

    @GetMapping("/user/{userId}")
    @Operation(summary = "List notifications for a user",
            description = "Paginated list of all notifications received by a user, newest first")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getUserNotifications(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result = notificationService.findByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/user/{userId}/type/{type}")
    @Operation(summary = "List notifications by user and type")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getUserNotificationsByType(
            @PathVariable String userId,
            @PathVariable NotificationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result =
                notificationService.findByUserAndType(userId, type, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/user/{userId}/status/{status}")
    @Operation(summary = "List notifications by user and status")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getUserNotificationsByStatus(
            @PathVariable String userId,
            @PathVariable NotificationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result =
                notificationService.findByUserAndStatus(userId, status, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/user/{userId}/unread-count")
    @Operation(summary = "Get unread notification count for a user")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(
            @PathVariable String userId
    ) {
        long count = notificationService.countUnreadByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(count, "Unread count: " + count));
    }

    // ── Admin: global queries ─────────────────────────────────────────────────

    @GetMapping
    @Operation(summary = "List all notifications (admin only)")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getAllNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result = notificationService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "List notifications by status")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getByStatus(
            @PathVariable NotificationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result = notificationService.findByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "List notifications by type")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getByType(
            @PathVariable NotificationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result = notificationService.findByType(type, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/reference")
    @Operation(summary = "List notifications by reference (blog post, repo, comment)")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getByReference(
            @RequestParam String referenceId,
            @RequestParam String referenceType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<NotificationResponse> result =
                notificationService.findByReference(referenceId, referenceType, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // ── Admin: operations ─────────────────────────────────────────────────────

    @PostMapping("/admin/retry-failed")
    @Operation(summary = "Retry all failed notifications (scheduled / manual trigger)")
    public ResponseEntity<ApiResponse<String>> retryFailed() {
        log.info("Admin request to retry failed notifications");
        notificationService.retryFailedNotifications();
        return ResponseEntity.ok(ApiResponse.success("Retry job triggered"));
    }

    @DeleteMapping("/admin/cleanup")
    @Operation(summary = "Delete old sent notifications")
    public ResponseEntity<ApiResponse<String>> cleanup(
            @RequestParam(defaultValue = "30") int daysOld
    ) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(daysOld);
        log.info("Admin request to cleanup notifications before {}", cutoff);
        notificationService.deleteOldSentNotifications(cutoff);
        return ResponseEntity.ok(ApiResponse.success("Cleanup completed"));
    }

    // ── Stats ─────────────────────────────────────────────────────────────────

    @GetMapping("/admin/stats")
    @Operation(summary = "Get notification statistics")
    public ResponseEntity<ApiResponse<NotificationStatsResponse>> getStats(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {
        LocalDateTime fromDate = from != null ? from : LocalDateTime.now().minusDays(7);
        LocalDateTime toDate   = to != null ? to : LocalDateTime.now();
        NotificationStatsResponse stats = notificationService.getStats(fromDate, toDate);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\NotificationType.java`

```
package com.final_project.notification_service.model;
public enum NotificationType {
    // Account lifecycle
    USER_REGISTERED,
    PASSWORD_CHANGED,
    EMAIL_VERIFIED,
    ACCOUNT_LOCKED,

    // Invitations
    SYSTEM_INVITATION,
    REPOSITORY_INVITATION,

    // Blog interactions
    BLOG_NEW_COMMENT,        // COMMENT_CREATED
    BLOG_COMMENT_REPLY,      // COMMENT_REPLIED
    BLOG_POST_LIKED,         // ARTICLE_LIKED
    BLOG_POST_SHARED,        // ARTICLE_SHARED
    BLOG_POST_PUBLISHED,     // ARTICLE_PUBLISHED


    REPOSITORY_PUSH,
    REPOSITORY_INVITATION_SENT,
    REPOSITORY_INVITATION_ACCEPTED,
    REPOSITORY_BRANCH_CREATED,
    REPOSITORY_BRANCH_MERGED,
    REPOSITORY_PULL_REQUEST_OPENED,
    REPOSITORY_PULL_REQUEST_MERGED,
    // Generic
    CUSTOM
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\RepositoryEventType.java`

```
package com.final_project.notification_service.model;


public enum RepositoryEventType {
    REPOSITORY_PUSHED,
    REPOSITORY_PULLED,
    REPOSITORY_FETCHED,
    REPOSITORY_CLONED,

    REPOSITORY_INVITATION_SENT,
    REPOSITORY_INVITATION_ACCEPTED,
    REPOSITORY_INVITATION_DECLINED,

    REPOSITORY_CREATED,
    REPOSITORY_DELETED,
    REPOSITORY_RENAMED,
    REPOSITORY_VISIBILITY_CHANGED,

    BRANCH_CREATED,
    BRANCH_DELETED,
    BRANCH_MERGED,

    COMMIT_CREATED,
    PULL_REQUEST_OPENED,
    PULL_REQUEST_MERGED,
    PULL_REQUEST_CLOSED
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\model\RepositoryMemberRecipient.java`

```
package com.final_project.notification_service.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryMemberRecipient {

    private String userId;
    private String name;
    private String email;
    private String role;
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\config\WebSocketConfig.java`

```
package com.final_project.notification_service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple memory-based message broker to carry messages back to clients
        config.enableSimpleBroker(
                "/topic",     // For broadcast messages
                "/queue",     // For user-specific messages
                "/repo"       // For repository-specific events
        );

        // Prefix for messages from client to server
        config.setApplicationDestinationPrefixes("/app");

        // User-specific message prefix
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors(new AuthHandshakeInterceptor())
                .withSockJS(); // Fallback for browsers that don't support WebSocket

        // Additional endpoint for native WebSocket clients (like Go CLI)
        registry.addEndpoint("/ws/native")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler());
    }

    /**
     * Custom handshake handler to assign unique session IDs
     */
    static class CustomHandshakeHandler extends DefaultHandshakeHandler {
        @Override
        protected Principal determineUser(
                org.springframework.http.server.ServerHttpRequest request,
                org.springframework.web.socket.WebSocketHandler wsHandler,
                Map<String, Object> attributes) {
            // Generate a unique principal for each connection
            return new StompPrincipal(UUID.randomUUID().toString());
        }
    }
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\config\OpenApiConfig.java`

```
package com.final_project.notification_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notification Service API")
                        .description(" Microservice responsible for delivering all system notifications.\n" +
                                     "\n" +
                                     "**Supported notification types:**\n" +
                                     "- User registration welcome emails\n" +
                                     "- Password change / reset security alerts\n" +
                                     "- System and repository invitations\n" +
                                     "- Blog post new comment alerts\n" +
                                     "- Comment reply alerts\n" +
                                     "\n" +
                                     "**Communication:** Async via Apache Kafka; sync via REST for admin/ops.\n" +
                                     "**Delivery channel:** Email (SMTP / Thymeleaf HTML templates).\n")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Platform Team")
                                .email("platform@app.com"))
                        .license(new License()
                                .name("Internal — All Rights Reserved")))
                .externalDocs(new ExternalDocumentation()
                        .description("Notification Service Confluence")
                        .url("https://wiki.app.com/notification-service"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token issued by the auth-service")));
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\config\KafkaConfig.java`

```
package com.final_project.notification_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@Slf4j
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final AppProperties appProperties;

    // ── Topics ───────────────────────────────────────────────────────────────

    @Bean public NewTopic userRegisteredTopic() {
        return TopicBuilder.name(appProperties.getKafka().getTopics().getUserRegistered())
                .partitions(3).replicas(1).build();
    }

    @Bean public NewTopic passwordChangedTopic() {
        return TopicBuilder.name(appProperties.getKafka().getTopics().getPasswordChanged())
                .partitions(3).replicas(1).build();
    }

    @Bean public NewTopic invitationSentTopic() {
        return TopicBuilder.name(appProperties.getKafka().getTopics().getInvitationSent())
                .partitions(3).replicas(1).build();
    }

    @Bean public NewTopic blogCommentedTopic() {
        return TopicBuilder.name(appProperties.getKafka().getTopics().getBlogCommented())
                .partitions(3).replicas(1).build();
    }

    @Bean public NewTopic commentRepliedTopic() {
        return TopicBuilder.name(appProperties.getKafka().getTopics().getCommentReplied())
                .partitions(3).replicas(1).build();
    }

    @Bean public NewTopic notificationDlqTopic() {
        return TopicBuilder.name(appProperties.getKafka().getTopics().getNotificationDlq())
                .partitions(1).replicas(1).build();
    }

    @Bean public NewTopic articleOperationsTopic(){
        return TopicBuilder.name(appProperties.getKafka().getTopics().getArticleOperations())
                .partitions(1).replicas(1).build();
    }
    @Bean
    public NewTopic repositoryOperationsTopic(){
        return TopicBuilder.name(appProperties.getKafka().getTopics().getRepositoryOperation())
                .partitions(1).replicas(1).build();
    }

    // ── Consumer factory ─────────────────────────────────────────────────────

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30_000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 10_000);

        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);
        deserializer.addTrustedPackages("com.app.notification.event");
        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(
                org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setCommonErrorHandler(errorHandler(kafkaTemplate));
        return factory;
    }

    // ── Producer factory ─────────────────────────────────────────────────────

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    // ── Dead-letter queue error handler ──────────────────────────────────────

    @Bean
    public CommonErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (record, ex) -> {
                    log.error("Sending record to DLQ. Topic={} Key={} Error={}",
                            record.topic(), record.key(), ex.getMessage());
                    return new org.apache.kafka.common.TopicPartition(
                            appProperties.getKafka().getTopics().getNotificationDlq(), 0);
                }
        );

        ExponentialBackOff backOff = new ExponentialBackOff(1_000L, 2.0);
        backOff.setMaxElapsedTime(30_000L);

        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backOff);
        handler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                com.fasterxml.jackson.core.JsonProcessingException.class
        );
        return handler;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\config\EmailConfig.java`

```
package com.final_project.notification_service.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
}

```

---

## `notification-service\src\main\java\com\final_project\notification_service\config\AsyncConfig.java`

```
package com.final_project.notification_service.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Comprehensive configuration for async processing, Redis, and serialization.
 *
 * Provides:
 * - Thread pool executor for async operations
 * - RedisConnectionFactory (Lettuce-based)
 * - RedisTemplate for cache operations
 * - ObjectMapper with Java time module support
 */
@Configuration
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    private final RedisProperties redisProperties;

    // ── Async Thread Pool ────────────────────────────────────────────────────

    /**
     * Configure thread pool executor for @Async methods.
     * Used for email sending and other background tasks.
     */
    @Override
    @Bean(name = "notificationExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);           // Minimum threads
        executor.setMaxPoolSize(20);           // Maximum threads
        executor.setQueueCapacity(200);        // Task queue size
        executor.setThreadNamePrefix("notif-async-");
        executor.setKeepAliveSeconds(60);      // Thread idle timeout
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        log.info("Async executor configured: corePoolSize=5, maxPoolSize=20");
        return executor;
    }

    // ── Redis Connection Factory ────────────────────────────────────────────

    /**
     * Create RedisConnectionFactory using Lettuce driver.
     * Used for idempotency and rate limiting.
     *
     * @return RedisConnectionFactory configured from properties
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        log.info("Redis connection factory created: host={}, port={}",
                redisProperties.getHost(), redisProperties.getPort());
        return factory;
    }

    // ── Redis Template ──────────────────────────────────────────────────────

    /**
     * Configure RedisTemplate with String serialization.
     * Used for all Redis operations (idempotency, rate limiting).
     *
     * @param connectionFactory Redis connection factory
     * @return Configured RedisTemplate
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use String serialization for keys and values
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);

        template.afterPropertiesSet();
        log.info("RedisTemplate configured with string serialization");
        return template;
    }

    // ── ObjectMapper ─────────────────────────────────────────────────────────

    /**
     *
     * @return Configured ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Register Java time module (LocalDateTime, Instant, etc)
        mapper.registerModule(new JavaTimeModule());

        // Don't serialize dates as timestamps, use ISO-8601 format
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Other useful settings
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        log.info("ObjectMapper configured with JavaTimeModule support");
        return mapper;
    }
}
```

---

## `notification-service\src\main\java\com\final_project\notification_service\config\AppProperties.java`

```
package com.final_project.notification_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Component
@ConfigurationProperties(prefix = "app")
@Validated
@Data
public class AppProperties {

    private Notification notification = new Notification();
    private Kafka kafka = new Kafka();

    @Data
    public static class Notification {
        @Email
        private String fromEmail = "no-reply@app.com";

        @NotBlank
        private String fromName = "App Notifications";

        @NotBlank
        private String baseUrl = "https://app.com";

        @Positive
        private int idempotencyTtlHours = 24;

        @Positive
        private int maxRetryAttempts = 3;

        @Positive
        private long retryDelayMs = 1000;

        private RateLimit rateLimit = new RateLimit();

        @Data
        public static class RateLimit {
            @Positive
            private int maxPerUserPerHour = 20;
        }
    }

    @Data
    public static class Kafka {
        private Topics topics = new Topics();

        @Data
        public static class Topics {
            private String userRegistered    = "user.registered";
            private String passwordChanged   = "password.changed";
            private String invitationSent    = "invitation.sent";
            private String blogCommented     = "blog.commented";
            private String commentReplied    = "comment.replied";
            private String notificationDlq   = "notification.dlq";
            private String repositoryOperation = "repository.operations";
            private String articleOperations = "article.operations";
        }
    }
}
```
