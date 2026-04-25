# Blog Service

## 1. Folder Structure

```text
blog-service/
  .env
  .gitattributes
  .gitignore
  .mvn/
    wrapper/
      maven-wrapper.properties
  HELP.md
  mvnw
  mvnw.cmd
  pom.xml
  src/
    main/
      java/
        com/
          final_project/
            blog_service/
              BlogServiceApplication.java
              client/
                FileServiceClient.java
                UserServiceClient.java
              config/
                OpenApiConfig.java
                RedisConfig.java
                SecurityConfig.java
                UserServiceFeignConfig.java
              controller/
                ArticleController.java
                FileUploadController.java
              dto/
                ArticlePreviewResponse.java
                ArticleResponse.java
                AuthorResponse.java
                CodeBlockDTO.java
                CommentEngagementResponse.java
                CommentResponse.java
                CommentThreadResponse.java
                ContentBlockDTO.java
                ContentBlockRequest.java
                ContentBlockResponse.java
                ContentResponse.java
                CreateArticleRequest.java
                CreateArticleWithFilesRequest.java
                CreateCommentRequest.java
                DividerBlockDTO.java
                EmbedBlockDTO.java
                ErrorResponse.java
                FileCdnUrlResponse.java
                FileMetadataResponse.java
                FileUploadResponse.java
                FlexibleContentBlockRequest.java
                HeadingBlockDTO.java
                ImageBlockDTO.java
                ImageUploadResponse.java
                LikeResponse.java
                MetadataResponse.java
                PaginatedResponse.java
                PublishArticleRequest.java
                QuoteBlockDTO.java
                SearchArticleRequest.java
                ShareRequest.java
                ShareResponse.java
                StatsResponse.java
                SuccessResponse.java
                TextBlockDTO.java
                UpdateArticleRequest.java
                UserAuthorResponse.java
                UserExistsResponse.java
                UserPreferencesResponse.java
                UserProfileResponse.java
                VideoBlockDTO.java
                VideoUploadResponse.java
              exception/
                FileNotFound.java
                FileServiceException.java
                FileUploadException.java
                GlobalExceptionHandler.java
                ResourceNotFoundException.java
                UnauthorizedException.java
                UserNotFoundException.java
                UserServiceException.java
                UserServiceUnavailableException.java
              model/
                Article.java
                ArticleStatus.java
                ArticleVisiblity.java
                Comment.java
                CommentStatus.java
                Like.java
                ReadingHistory.java
                Share.java
                SharedPlatform.java
              repo/
                ArticleRepository.java
                CommentRepository.java
                LikeRepository.java
                ReadingHistoryRepository.java
                ShareRepository.java
              service/
                ArticleService.java
                FileUploadService.java
                UserCacheService.java
              utile/
                ContentBlockValidator.java
                ReadTimeCalculator.java
                SlugUtil.java
      resources/
        application.yaml
        static/
        templates/
    test/
      java/
        com/
          final_project/
            blog_service/
              BlogServiceApplicationTests.java
```

## 2. File Contents

The sections below embed the current file contents from the blog-service microservice. Generated build output under target/ is excluded.

### .env

`$lang
KEYCLOAK_SERVER_URL=localhost:8444
KEYCLOAK_REALM=fina_project
RABBIT_PASSWORD=guest
RABBIT_USERNAME=guest
RABBIT_PORT=5672
RABBIT_HOST=localhost
```

### .gitattributes

`$lang
/mvnw text eol=lf
*.cmd text eol=crlf
```

### .gitignore

`$lang
HELP.md
target/
.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/
```

### .mvn/wrapper/maven-wrapper.properties

`$lang
wrapperVersion=3.3.4
distributionType=only-script
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.14/apache-maven-3.9.14-bin.zip
```

### HELP.md

`$lang
# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.final_project.blog-service' is invalid and this project uses 'com.final_project.blog_service' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.13/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.13/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.13/reference/web/servlet.html)
* [Validation](https://docs.spring.io/spring-boot/3.5.13/reference/io/validation.html)
* [Spring Security](https://docs.spring.io/spring-boot/3.5.13/reference/web/spring-security.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

```

### mvnw

`$lang
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

### mvnw.cmd

`$lang
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

### pom.xml

`$lang
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
	<artifactId>blog-service</artifactId>
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
		<spring-cloud.version>2025.0.1</spring-cloud.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<scope>annotationProcessor</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.8.16</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.12.3</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-jose</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-resource-server</artifactId>
		</dependency>
		<dependency>
			<groupId>io.github.resilience4j</groupId>
			<artifactId>resilience4j-circuitbreaker</artifactId>
			<version>2.3.0</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>io.github.resilience4j</groupId>
			<artifactId>resilience4j-retry</artifactId>
			<version>2.4.0</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
	</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
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
			</plugin>
		</plugins>
	</build>

</project>
```

### src/main/java/com/final_project/blog_service/BlogServiceApplication.java

`$lang
package com.final_project.blog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BlogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogServiceApplication.class, args);
	}

}
```

### src/main/java/com/final_project/blog_service/client/FileServiceClient.java

`$lang
package com.final_project.blog_service.client;

import com.final_project.blog_service.dto.response.FileCdnUrlResponse;
import com.final_project.blog_service.dto.response.FileMetadataResponse;
import com.final_project.blog_service.dto.response.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "file-service",
        url = "${app.service.file-url}"
)
public interface FileServiceClient {

    @GetMapping("/file/blog/post/{postId}")
    String getPostBlog(@PathVariable("postId") String postId);

    @PostMapping(value = "/file/blog/post/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String postPost(
            @PathVariable("postId") String postId,
            @RequestPart("file") MultipartFile file
    );

    @PostMapping(value = "/file/blog/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileUploadResponse uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("fileType") String fileType,
            @RequestParam(value = "metadata", required = false) String metadata
    );

    @GetMapping("/file/blog/{fileId}")
    FileMetadataResponse getFileMetadata(@PathVariable("fileId") String fileId);

    @DeleteMapping("/file/blog/{fileId}")
    void deleteFile(@PathVariable("fileId") String fileId);

    @GetMapping("/file/blog/{fileId}/url")
    FileCdnUrlResponse getCdnUrl(@PathVariable("fileId") String fileId);
}
```

### src/main/java/com/final_project/blog_service/client/UserServiceClient.java

`$lang
package com.final_project.blog_service.client;

import com.final_project.blog_service.config.UserServiceFeignConfig;
import com.final_project.blog_service.dto.response.UserAuthorResponse;
import com.final_project.blog_service.dto.response.UserExistsResponse;
import com.final_project.blog_service.dto.response.UserPreferencesResponse;
import com.final_project.blog_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "auth-service",
        url = "${app.service.auth-url}",
        configuration = UserServiceFeignConfig.class
)
public interface UserServiceClient {

    /**
     * Get user profile by ID
     * Called when article author info is needed
     */
    @GetMapping("/api/v1/users/{userId}")
    UserProfileResponse getUserProfile(@PathVariable String userId);

    /**
     * Get author by id
     * Called for cross-service validation
     */
    @GetMapping("/api/v1/users/author/{id}")
    UserAuthorResponse getUserAuthor(@PathVariable String id);

    /**
     * Get user by email
     * Called for cross-service validation
     */
    @GetMapping("/api/v1/users/email/{email}")
    UserProfileResponse getUserByEmail(@PathVariable String email);

    /**
     * Verify user exists
     * Called before creating article or comment
     */
    @GetMapping("/api/v1/users/{userId}/exists")
    UserExistsResponse checkUserExists(@PathVariable String userId);

    /**
     * Get multiple user profiles
     * Called for bulk author info retrieval
     */
    @PostMapping("/api/v1/users/batch")
    List<UserProfileResponse> getMultipleUsers(@RequestBody List<String> userIds);

    /**
     * Get user preferences
     * Called for personalization
     */
    @GetMapping("/api/v1/users/{userId}/preferences")
    UserPreferencesResponse getUserPreferences(@PathVariable String userId);
}






```

### src/main/java/com/final_project/blog_service/config/OpenApiConfig.java

`$lang
package com.final_project.blog_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI Configuration
 *
 * Configures Swagger/OpenAPI documentation for the Article Service
 *
 * Access at: http://localhost:8081/swagger-ui.html
 * API Docs JSON: http://localhost:8081/v3/api-docs
 * API Docs YAML: http://localhost:8081/v3/api-docs.yaml
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getApiInfo())
                .components(getComponents())
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    /**
     * API Information
     */
    private Info getApiInfo() {
        return new Info()
                .title("Article Service API")
                .version("1.0.0")
                .description("""
                **Medium-Like Blogging Platform - Article Service**
                
                A production-grade Spring Boot microservice for managing blog articles,
                comments, engagement, and user interactions.
                
                ## Features
                - ✅ Create, read, update, and publish articles
                - ✅ Block-based flexible content (text, images, videos, code, quotes, embeds)
                - ✅ Hierarchical threaded comments and replies
                - ✅ Engagement tracking (likes, shares, read metrics)
                - ✅ JWT authentication with User Service integration
                - ✅ User profile caching with fallback mechanism
                - ✅ MongoDB for flexible document storage
                - ✅ Redis caching for performance
                - ✅ Full-text search capabilities
                
                ## Security
                - All write operations require JWT authentication
                - JWT tokens obtained from User Service
                - Only article authors can edit/delete their articles
                - Only comment authors can delete their comments
                - Input validation on all endpoints
                
                ## API Structure
                - **Base Path**: `/api/v1`
                - **Articles**: `/articles` - CRUD operations on blog posts
                - **Comments**: `/articles/{id}/comments` - Threaded discussions
                - **Engagement**: `/articles/{id}/like`, `/articles/{id}/share` - User interactions
                
                ## Content Blocks
                Articles support flexible content blocks:
                - `text` - Rich text content
                - `heading` - Headers with levels 1-6
                - `image` - Images with captions
                - `video` - Video embeds
                - `code` - Code snippets with syntax highlighting
                - `quote` - Quoted text with attribution
                - `embed` - External embeds (YouTube, Twitter, etc.)
                - `divider` - Visual separator
                
                ## Authentication
                All protected endpoints require JWT Bearer token in Authorization header:
                ```
                Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
                ```
                
                ## Response Format
                - Successful responses include status code and data
                - Error responses include error code, message, and timestamp
                - Paginated responses include pagination metadata
                
                ## Pagination
                List endpoints support pagination with:
                - `page` - Zero-indexed page number (default: 0)
                - `pageSize` - Items per page (default: 20, max: 100)
                
                ## Rate Limiting
                - Read operations: 100 requests/minute per user
                - Write operations: 20 requests/minute per user
                - Publish operations: 5 requests/minute per user
                """)
                .contact(new Contact()
                        .name("Backend Team")
                        .email("backend@blog.com")
                        .url("https://blog.com/support"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"))
                .termsOfService("https://blog.com/terms");
    }


    /**
     * Security Schemes and Components
     */
    private Components getComponents() {
        return new Components()
                .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("""
                        JWT Bearer Token obtained from User Service.
                        
                        **How to get a token:**
                        1. Call User Service login endpoint: POST /api/v1/auth/login
                        2. Provide email and password
                        3. Receive JWT token in response
                        4. Include token in Authorization header for all requests
                        
                        **Token Format:**
                        ```
                        Authorization: Bearer <your_jwt_token_here>
                        ```
                        
                        **Token Claims:**
                        - `sub` (subject) - User ID
                        - `email` - User email
                        - `iat` (issued at) - Token creation timestamp
                        - `exp` (expiration) - Token expiration timestamp (24 hours)
                        """)
                );
    }
}

```

### src/main/java/com/final_project/blog_service/config/RedisConfig.java

`$lang
package com.final_project.blog_service.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.blog_service.dto.response.UserProfileResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);

        return template;
    }
    @Bean
    public RedisTemplate<String, UserProfileResponse> userProfileRedisTemplate(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {
        RedisTemplate<String, UserProfileResponse> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer keySerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<UserProfileResponse> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, UserProfileResponse.class);

        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
```

### src/main/java/com/final_project/blog_service/config/SecurityConfig.java

`$lang
package com.final_project.blog_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health", "/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Set<GrantedAuthority> authorities = new HashSet<>();

            Collection<GrantedAuthority> scopeAuthorities = scopeConverter.convert(jwt);
            if (scopeAuthorities != null) {
                authorities.addAll(scopeAuthorities);
            }

            Object rolesClaim = jwt.getClaim("roles");
            if (rolesClaim instanceof Collection<?> roleList) {
                for (Object role : roleList) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
            }

            return authorities;
        });

        return converter;
    }
}
```

### src/main/java/com/final_project/blog_service/config/UserServiceFeignConfig.java

`$lang
package com.final_project.blog_service.config;

import com.final_project.blog_service.exception.UnauthorizedException;
import com.final_project.blog_service.exception.UserNotFoundException;
import com.final_project.blog_service.exception.UserServiceException;
import com.final_project.blog_service.exception.UserServiceUnavailableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UserServiceFeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            log.error("User Service error: {} {}", response.status(), response.reason());

            switch (response.status()) {
                case 400:
                    return new UserServiceException("Bad request to User Service");
                case 401:
                case 403:
                    return new UnauthorizedException("Unauthorized access to User Service");
                case 404:
                    return new UserNotFoundException("User not found in User Service");
                case 500:
                case 502:
                case 503:
                case 504:
                    return new UserServiceUnavailableException("User Service is unavailable");
                default:
                    return new UserServiceException("User Service error: " + response.reason());
            }
        };
    }
}

```

### src/main/java/com/final_project/blog_service/controller/ArticleController.java

`$lang
package com.final_project.blog_service.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.blog_service.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.final_project.blog_service.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Article Controller - REST API for Blog Articles
 *
 * All endpoints are documented with Swagger/OpenAPI annotations for:
 * - Automatic API documentation
 * - Interactive Swagger UI
 * - Request/response validation
 * - Security requirements
 * - Error handling
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
@Tags({
        @Tag(
                name = "Articles",
                description = "Article management endpoints - create, read, update, publish articles"
        )
})
public class ArticleController {

    private final ArticleService articleService;
    private final ObjectMapper objectMapper;

    /**
     * Create article with multipart support
     *
     * Flexible endpoint supporting:
     * - Pure JSON article (no files)
     * - Articles with embedded image/video files
     * - Mixed content blocks
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create article (with file upload support)",
            description = """
            Creates a new article with flexible content.
            
            Supports both:
            1. Pure JSON articles (application/json) - for text-only content
            2. Multipart articles with embedded files - for rich media content
            
            When using multipart:
            - Send 'article' as JSON with file references
            - Send image/video files separately
            - Reference files by their multipart field name in JSON
            
            Example flow:
            1. Use file upload endpoints first to get fileIds
            2. Create article JSON referencing those fileIds
            3. Send as multipart with optional additional files
            """,
            tags = {"Articles"},
            operationId = "createArticleWithFiles"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - validation failed or malformed JSON"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token missing or invalid"
            ),
            @ApiResponse(
                    responseCode = "413",
                    description = "Payload too large"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> createArticleWithFiles(
            @RequestPart("article") String articleJsonString,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "files", required = false) MultipartFile[] additionalFiles,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        try {
            // Parse article JSON
            CreateArticleWithFilesRequest request = objectMapper.readValue(
                    articleJsonString,
                    CreateArticleWithFilesRequest.class
            );

            // Process files and update file references in content blocks
            request = processAndMapFiles(request, coverImage, additionalFiles, userId);

            // Create article
            ArticleResponse response = articleService.createArticleWithFiles(userId, request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            log.error("Failed to parse article JSON: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid article JSON format", e);
        }
    }

    /**
     * Update article with new files
     */
    @PutMapping(value = "/{articleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update article (with file support)",
            description = "Updates an article and optionally uploads new files",
            tags = {"Articles"},
            operationId = "updateArticleWithFiles"
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> updateArticleWithFiles(
            @PathVariable String articleId,
            @RequestPart("article") String articleJsonString,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "files", required = false) MultipartFile[] additionalFiles,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        try {
            UpdateArticleRequest request = objectMapper.readValue(
                    articleJsonString,
                    UpdateArticleRequest.class
            );

            ArticleResponse response = articleService.updateArticle(articleId, userId, request);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid article JSON format", e);
        }
    }
    /**
     * Alternative: Create article with pure JSON (without files)
     * For backward compatibility and text-only articles
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create article (JSON only)",
            description = "Creates an article using pure JSON. Use file upload endpoints separately for files.",
            tags = {"Articles"},
            operationId = "createArticleJson"
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> createArticleJson(
            @Valid @RequestBody CreateArticleRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        ArticleResponse response = articleService.createArticle(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    /**
     * Create a new draft article
     */
    @PostMapping("/{userId}")
    @Operation(
            summary = "Create a new article (draft)",
            description = "Creates a new draft article. Requires authentication (JWT token).",
            tags = {"Articles"},
            operationId = "createArticle"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token missing or invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ArticleResponse> createArticle(
            @Valid @RequestBody CreateArticleRequest request,
            @PathVariable String userId
    ) {

        ArticleResponse response = articleService.createArticle(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get article by ID
     */
    @GetMapping("/{articleId}")
    @Operation(
            summary = "Get article by ID",
            description = "Retrieves a single article by its ID. Public endpoint.",
            tags = {"Articles"},
            operationId = "getArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The unique identifier of the article (MongoDB ObjectId)",
                    example = "6507a1b2c3d4e5f6g7h8i9j0",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(type = "string")
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ArticleResponse> getArticle(
            @PathVariable
            @Parameter(description = "Article ID")
            String articleId
    ) {
        ArticleResponse response = articleService.getArticleById(articleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/slug/{slug}")
    @Operation(
            summary = "Get article by slug",
            description = "Retrieves an article using its URL-friendly slug. Public endpoint.",
            tags = {"Articles"},
            operationId = "getArticleBySlug"
    )
    @Parameters({
            @Parameter(
                    name = "slug",
                    description = "The URL-friendly article identifier (e.g., 'my-first-blog-post')",
                    example = "my-first-blog-post",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<ArticleResponse> getArticleBySlug(
            @PathVariable String slug
    ) {
        ArticleResponse response = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(response);
    }

    /**
     * Update article
     */
    @PutMapping("/{articleId}")
    @Operation(
            summary = "Update article",
            description = "Updates an existing article. Only the author can update. Article can be in DRAFT or PUBLISHED status.",
            tags = {"Articles"},
            operationId = "updateArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    example = "6507a1b2c3d4e5f6g7h8i9j0",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Not the article author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable String articleId,
            @Valid @RequestBody UpdateArticleRequest request,
            @AuthenticationPrincipal Jwt jwt
            ) {

        String userId = jwt.getSubject();
        ArticleResponse response = articleService.updateArticle(articleId, userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Publish article (transition from DRAFT to PUBLISHED)
     */
    @PatchMapping("/{articleId}/publish")
    @Operation(
            summary = "Publish article",
            description = "Publishes a draft article. Transitions status from DRAFT to PUBLISHED. Only author can publish.",
            tags = {"Articles"},
            operationId = "publishArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article published successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - article not in DRAFT status"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> publishArticle(
            @PathVariable String articleId,
            @Valid @RequestBody PublishArticleRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        ArticleResponse response = articleService.publishArticle(articleId, userId, request);
        return ResponseEntity.ok(response);
    }


    /**
     * Delete/Archive article
     */
    @DeleteMapping("/{articleId}")
    @Operation(
            summary = "Delete/Archive article",
            description = "Soft deletes (archives) an article. Only the author can delete. Data is retained for GDPR compliance.",
            tags = {"Articles"},
            operationId = "deleteArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Article deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable String articleId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        articleService.deleteArticle(articleId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get published articles (feed)
     */
    @GetMapping
    @Operation(
            summary = "Get published articles (feed)",
            description = "Retrieves a paginated list of all published articles. Perfect for the main feed. Public endpoint.",
            tags = {"Articles"},
            operationId = "getPublishedArticles"
    )
    @Parameters({
            @Parameter(
                    name = "page",
                    description = "Page number (zero-indexed). Default is 0.",
                    example = "0",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "integer", minimum = "0")
            ),
            @Parameter(
                    name = "pageSize",
                    description = "Number of articles per page. Default is 20, max 100.",
                    example = "20",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "integer", minimum = "1", maximum = "100")
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    public ResponseEntity<PaginatedResponse<ArticlePreviewResponse>> getPublishedArticles(
            @RequestParam(defaultValue = "0")
            @Parameter(description = "Page number")
            int page,

            @RequestParam(defaultValue = "20")
            @Parameter(description = "Page size")
            int pageSize
    ) {
        PaginatedResponse<ArticlePreviewResponse> response = articleService.getPublishedArticles(page, pageSize);
        return ResponseEntity.ok(response);
    }

    /**
     * Get author's articles
     */
    @GetMapping("/author/{authorId}")
    @Operation(
            summary = "Get author's articles",
            description = "Retrieves all published articles by a specific author. Public endpoint.",
            tags = {"Articles"},
            operationId = "getAuthorArticles"
    )
    @Parameters({
            @Parameter(
                    name = "authorId",
                    description = "The author's user ID",
                    example = "user_123",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "page",
                    description = "Page number (zero-indexed)",
                    example = "0",
                    required = false,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "pageSize",
                    description = "Number of articles per page",
                    example = "20",
                    required = false,
                    in = ParameterIn.QUERY
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author not found"
            )
    })
    public ResponseEntity<PaginatedResponse<ArticlePreviewResponse>> getAuthorArticles(
            @PathVariable String authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {

        PaginatedResponse<ArticlePreviewResponse> response = articleService.getUserArticles(authorId, page, pageSize);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/author/{authorId}/{articleId}")
    @Operation(
            summary = "Get author's specific  article",
            description = "Retrieves a specific published article by a specific author and article id. Public endpoint.",
            tags = {"Articles"},
            operationId = "getAuthorArticles"
    )
    @Parameters({
            @Parameter(
                    name = "authorId",
                    description = "The author's user ID",
                    example = "user_123",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "articleId",
                    description = "the article Id",
                    example = "0",
                    required = false,
                    in = ParameterIn.PATH
            )

    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author not found"
            )
    })
    public ResponseEntity<ArticleResponse> getArticleAuthor(
            @PathVariable String authorId,
            @PathVariable String articleId
    ){
        ArticleResponse response = articleService.getArticleByAuthorAndId(authorId, articleId);
        return ResponseEntity.ok(response);
    }



    /**
     * Post a comment on an article
     */
    @PostMapping("/{articleId}/comments")
    @Operation(
            summary = "Post a comment",
            description = "Posts a top-level comment on an article. Requires authentication.",
            tags = {"Comments"},
            operationId = "postComment"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to comment on",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment posted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    public ResponseEntity<CommentResponse> postComment(
            @PathVariable String articleId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        CommentResponse response = articleService.postComment(articleId, userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get comments on an article (threaded)
     */
    @GetMapping("/{articleId}/comments")
    @Operation(
            summary = "Get article comments (threaded)",
            description = "Retrieves all comments on an article in a threaded structure. Public endpoint.",
            tags = {"Comments"},
            operationId = "getArticleComments"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "page",
                    description = "Page number",
                    example = "0",
                    required = false,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "pageSize",
                    description = "Comments per page",
                    example = "10",
                    required = false,
                    in = ParameterIn.QUERY
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    public ResponseEntity<PaginatedResponse<CommentThreadResponse>> getArticleComments(
            @PathVariable String articleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PaginatedResponse<CommentThreadResponse> response = articleService.getArticleComments(articleId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    /**
     * Reply to a comment
     */
    @PostMapping("/{articleId}/comments/{parentCommentId}/reply")
    @Operation(
            summary = "Reply to a comment",
            description = "Posts a reply to an existing comment. Creates a threaded discussion.",
            tags = {"Comments"},
            operationId = "replyToComment"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "parentCommentId",
                    description = "The comment ID to reply to",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Reply posted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article or comment not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommentResponse> replyToComment(
            @PathVariable String articleId,
            @PathVariable String parentCommentId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        CommentResponse response = articleService.replyToComment(articleId, parentCommentId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Delete a comment
     */
    @DeleteMapping("/comments/{commentId}")
    @Operation(
            summary = "Delete a comment",
            description = "Deletes (soft delete) a comment. Only the author can delete.",
            tags = {"Comments"},
            operationId = "deleteComment"
    )
    @Parameters({
            @Parameter(
                    name = "commentId",
                    description = "The comment ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Comment deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteComment(
            @PathVariable String commentId,
            @AuthenticationPrincipal Jwt jwt
    ) {


        String userId = jwt.getSubject();
        articleService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
    /**
     * Like an article
     */
    @PostMapping("/{articleId}/like")
    @Operation(
            summary = "Like an article",
            description = "Adds a like to an article. Each user can like each article only once.",
            tags = {"Engagement"},
            operationId = "likeArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to like",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Article liked successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LikeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Article already liked by this user"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<LikeResponse> likeArticle(
            @PathVariable String articleId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        LikeResponse response = articleService.likeArticle(articleId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Unlike an article
     */
    @DeleteMapping("/{articleId}/like")
    @Operation(
            summary = "Unlike an article",
            description = "Removes a like from an article.",
            tags = {"Engagement"},
            operationId = "unlikeArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to unlike",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Article unliked successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> unlikeArticle(
            @PathVariable String articleId,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        articleService.unlikeArticle(articleId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Share an article
     */
    @PostMapping("/{articleId}/share")
    @Operation(
            summary = "Share an article",
            description = "Tracks the sharing of an article on various platforms (Twitter, Facebook, LinkedIn, email, etc.)",
            tags = {"Engagement"},
            operationId = "shareArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to share",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Share tracked successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShareResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ShareResponse> shareArticle(
            @PathVariable String articleId,
            @Valid @RequestBody ShareRequest request,
            @AuthenticationPrincipal  Jwt jwt
    ) {
        String userId = jwt.getSubject();

        ShareResponse response = articleService.shareArticle(articleId, userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    private CreateArticleWithFilesRequest processAndMapFiles(
            CreateArticleWithFilesRequest request,
            MultipartFile coverImage,
            MultipartFile[] additionalFiles,
            String userId
    ) {
        // Process cover image if provided
        if (coverImage != null && !coverImage.isEmpty()) {
            // Upload cover image would be done here
            // This is handled by separate file upload endpoints
            log.debug("Cover image provided but should be uploaded separately");
        }

        // Additional files are processed by their references in content blocks
        // The content blocks should reference files by fileId from prior uploads

        return request;
    }
}
```

### src/main/java/com/final_project/blog_service/controller/FileUploadController.java

`$lang
package com.final_project.blog_service.controller;

import com.final_project.blog_service.dto.*;
import com.final_project.blog_service.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * File Upload Controller
 *
 * Handles file uploads for article content
 * Supports images, videos, and other media files
 * Integrates with File Service (MinIO)
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(
        name = "File Uploads",
        description = "File upload endpoints for article content (images, videos, etc.)"
)
public class FileUploadController {

    private final FileUploadService fileUploadService;

    /**
     * Upload an image file
     */
    @PostMapping(value = "/upload/image", consumes = "multipart/form-data")
    @Operation(
            summary = "Upload image",
            description = "Uploads an image file for use in article content blocks. Supports JPEG, PNG, WebP, GIF.",
            tags = {"File Uploads"},
            operationId = "uploadImage"
    )
    @Parameters({
            @Parameter(
                    name = "file",
                    description = "Image file (JPEG, PNG, WebP, GIF). Max size: 10MB",
                    required = true,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "alt",
                    description = "Alternative text for accessibility",
                    required = false,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "caption",
                    description = "Image caption",
                    required = false,
                    in = ParameterIn.QUERY
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Image uploaded successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ImageUploadResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid file or parameters"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "413",
                    description = "File too large (max 10MB)"
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Unsupported media type"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ImageUploadResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String alt,
            @RequestParam(required = false) String caption,
            Authentication authentication
    ) {
        log.info("POST /api/v1/files/upload/image - Upload image");

        String userId = authentication.getName();
        ImageUploadResponse response = fileUploadService.uploadImage(file, alt, caption, userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Upload a video file
     */
    @PostMapping(value = "/upload/video", consumes = "multipart/form-data")
    @Operation(
            summary = "Upload video",
            description = "Uploads a video file for use in article content blocks. Supports MP4, WebM, Ogg, MOV.",
            tags = {"File Uploads"},
            operationId = "uploadVideo"
    )
    @Parameters({
            @Parameter(
                    name = "file",
                    description = "Video file (MP4, WebM, Ogg, MOV). Max size: 500MB",
                    required = true,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "title",
                    description = "Video title",
                    required = false,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "description",
                    description = "Video description",
                    required = false,
                    in = ParameterIn.QUERY
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Video uploaded successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VideoUploadResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid file or parameters"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "413",
                    description = "File too large (max 500MB)"
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Unsupported media type"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<VideoUploadResponse> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        VideoUploadResponse response = fileUploadService.uploadVideo(file, title, description, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a file
     */
    @DeleteMapping("/{fileId}")
    @Operation(
            summary = "Delete file",
            description = "Deletes a file from storage. Only the uploader can delete their files.",
            tags = {"File Uploads"},
            operationId = "deleteFile"
    )
    @Parameters({
            @Parameter(
                    name = "fileId",
                    description = "The ID of the file to delete",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "File deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the file uploader"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "File not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteFile(
            @PathVariable String fileId,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        fileUploadService.deleteFile(fileId, userId);
        return ResponseEntity.noContent().build();
    }
}
```

### src/main/java/com/final_project/blog_service/dto/ArticlePreviewResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Article Preview Response",
        description = "Summary view of an article for lists and feeds"
)
public class ArticlePreviewResponse {

    @Schema(
            title = "Article ID",
            example = "6507a1b2c3d4e5f6g7h8i9j0"
    )
    private String id;
    @Schema(
            title = "Article Slug",
            example = "my-first-blog-post"
    )
    private String slug;
    @Schema(
            title = "Article Title",
            example = "My First Blog Post"
    )
    private String title;
    @Schema(
            title = "Article Subtitle",
            example = "An exciting journey"
    )
    private String subtitle;
    @Schema(
            title = "Cover Image URL",
            example = "https://cdn.example.com/cover.jpg"
    )
    private String coverImageUrl;
    @Schema(
            title = "Article Description",
            description = "Short description or excerpt"
    )
    private String description;
    @Schema(
            title = "Article Statistics",
            description = "Engagement metrics"
    )
    private StatsResponse stats;
    @Schema(
            title = "Article Author",
            description = "Author information"
    )
    private AuthorResponse author;
    @Schema(
            title = "Published Date",
            example = "2024-01-15T10:30:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime publishedAt;
    @Schema(
            title = "Estimated Read Time",
            description = "Minutes to read",
            example = "5"
    )
    private Integer estimatedReadTime;
    @Schema(
            title = "Tags",
            example = "[\"javascript\", \"web-dev\"]"
    )
    private List<String> tags;
}
```

### src/main/java/com/final_project/blog_service/dto/ArticleResponse.java

`$lang
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.ArticleStatus;
import com.final_project.blog_service.model.ArticleVisiblity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Article Response",
        description = "Complete article details with content, metadata, and engagement stats"
)
public class ArticleResponse {

    @Schema(
            title = "Article ID",
            description = "Unique identifier for the article (MongoDB ObjectId)",
            example = "6507a1b2c3d4e5f6g7h8i9j0"
    )
    private String id;

    @Schema(
            title = "Article Slug",
            description = "URL-friendly identifier for the article",
            example = "my-first-blog-post",
            pattern = "^[a-z0-9]+(-[a-z0-9]+)*$"
    )
    private String slug;

    @Schema(
            title = "Article Title",
            example = "My First Blog Post"
    )
    private String title;

    @Schema(
            title = "Article Subtitle",
            example = "An exciting journey into blogging"
    )
    private String subtitle;

    @Schema(
            title = "Article Content",
            description = "Structured content blocks"
    )
    private ContentResponse content;

    @Schema(
            title = "Article Metadata",
            description = "Tags, category, SEO information"
    )
    private MetadataResponse metadata;

    @Schema(
            title = "Article Status",
            description = "Current status of the article",
            example = "PUBLISHED",
            allowableValues = {"DRAFT", "PUBLISHED", "ARCHIVED"}
    )
    private ArticleStatus status;

    @Schema(
            title = "Article Visibility",
            description = "Who can access this article",
            example = "PUBLIC",
            allowableValues = {"PUBLIC", "PRIVATE", "UNLISTED"}
    )
    private ArticleVisiblity visibility;

    @Schema(
            title = "Article Statistics",
            description = "Engagement and view statistics"
    )
    private StatsResponse stats;

    @Schema(
            title = "Article Author",
            description = "Author profile information"
    )
    private AuthorResponse author;

    @Schema(
            title = "Published Date",
            description = "When the article was published",
            example = "2024-01-15T10:30:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime publishedAt;

    @Schema(
            title = "Updated Date",
            description = "When the article was last updated",
            example = "2024-01-15T15:45:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime updatedAt;

    @Schema(
            title = "Created Date",
            description = "When the article was created",
            example = "2024-01-15T10:00:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;

    @Schema(
            title = "Estimated Read Time",
            description = "Estimated time to read the article in minutes",
            example = "5",
            minimum = "1"
    )
    private Integer estimatedReadTime;
}
```

### src/main/java/com/final_project/blog_service/dto/AuthorResponse.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Author Response",
        description = "Author profile information"
)
public class AuthorResponse {

    @Schema(
            title = "Author ID",
            example = "user_123"
    )
    private String id;

    @Schema(
            title = "Author Display Name",
            example = "John Doe"
    )
    private String displayName;

    @Schema(
            title = "Author Profile Image",
            example = "https://cdn.example.com/profiles/john.jpg"
    )
    private String profileImageUrl;

    @Schema(
            title = "Total Articles",
            description = "Number of articles written by this author",
            example = "15"
    )
    private Long totalArticles;
}
```

### src/main/java/com/final_project/blog_service/dto/CodeBlockDTO.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Code Block - Code snippets with syntax highlighting
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Code Block",
        description = "Code snippet with language specification",
        example = "{\"type\": \"code\", \"data\": {\"language\": \"javascript\", \"code\": \"const x = 1;\"}}"
)
public class CodeBlockDTO extends ContentBlockDTO {

    @NotBlank(message = "Code content is required")
    @Size(max = 50000, message = "Code must not exceed 50000 characters")
    @Schema(
            title = "Code Content",
            description = "The code snippet",
            example = "const greeting = 'Hello, World!';\\nconsole.log(greeting);"
    )
    private String code;

    @Size(max = 100, message = "Language must not exceed 100 characters")
    @Schema(
            title = "Programming Language",
            description = "Language for syntax highlighting",
            example = "javascript",
            allowableValues = {
                    "javascript", "python", "java", "cpp", "csharp", "go", "rust",
                    "ruby", "php", "swift", "kotlin", "typescript", "sql", "html",
                    "css", "bash", "shell", "plaintext"
            }
    )
    private String language;

    @Builder.Default
    @Schema(
            title = "Show Line Numbers",
            description = "Whether to display line numbers",
            example = "true"
    )
    private Boolean showLineNumbers = false;

    public CodeBlockDTO(String type, String code, String language) {
        this.type = type;
        this.code = code;
        this.language = language;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/CommentEngagementResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Comment Engagement Response",
        description = "Engagement metrics for a comment"
)
public class CommentEngagementResponse {

    @Schema(
            title = "Likes",
            description = "Number of likes on this comment",
            example = "5"
    )
    private Long likes;

    @Schema(
            title = "Reply Count",
            description = "Number of replies to this comment",
            example = "2"
    )
    private Long replyCount;
}
```

### src/main/java/com/final_project/blog_service/dto/CommentResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Comment Response",
        description = "Complete comment details"
)
public class CommentResponse {

    @Schema(
            title = "Comment ID",
            example = "comment_123"
    )
    private String id;

    @Schema(
            title = "Article ID",
            description = "The article this comment belongs to",
            example = "article_123"
    )
    private String articleId;

    @Schema(
            title = "Parent Comment ID",
            description = "If this is a reply, the ID of the parent comment",
            example = "comment_122",
            nullable = true
    )
    private String parentCommentId;

    @Schema(
            title = "Comment Body",
            example = "Great article!"
    )
    private String body;

    @Schema(
            title = "Comment Author",
            description = "Author profile information"
    )
    private AuthorResponse author;

    @Schema(
            title = "Comment Engagement",
            description = "Likes and reply count"
    )
    private CommentEngagementResponse engagement;

    @Schema(
            title = "Created At",
            example = "2024-01-15T10:35:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;

    @Schema(
            title = "Edited At",
            description = "When the comment was last edited",
            example = "2024-01-15T11:00:00",
            type = "string",
            format = "date-time",
            nullable = true
    )
    private LocalDateTime editedAt;

    @Schema(
            title = "Reply Count",
            description = "Number of direct replies to this comment",
            example = "3"
    )
    private Integer replyCount;
}
```

### src/main/java/com/final_project/blog_service/dto/CommentThreadResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Comment Thread Response",
        description = "A comment with its nested replies (threaded structure)"
)
public class CommentThreadResponse {

    @Schema(
            title = "Comment",
            description = "The main comment"
    )
    private CommentResponse comment;

    @ArraySchema(
            schema = @Schema(implementation = CommentThreadResponse.class),
            arraySchema = @Schema(
                    title = "Replies",
                    description = "Nested replies to this comment"
            )
    )
    private List<CommentThreadResponse> replies;
}
```

### src/main/java/com/final_project/blog_service/dto/ContentBlockDTO.java

`$lang
package com.final_project.blog_service.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base Content Block with Type Discriminator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextBlockDTO.class, name = "text"),
        @JsonSubTypes.Type(value = HeadingBlockDTO.class, name = "heading"),
        @JsonSubTypes.Type(value = ImageBlockDTO.class, name = "image"),
        @JsonSubTypes.Type(value = VideoBlockDTO.class, name = "video"),
        @JsonSubTypes.Type(value = CodeBlockDTO.class, name = "code"),
        @JsonSubTypes.Type(value = QuoteBlockDTO.class, name = "quote"),
        @JsonSubTypes.Type(value = EmbedBlockDTO.class, name = "embed"),
        @JsonSubTypes.Type(value = DividerBlockDTO.class, name = "divider")
})
@Schema(
        title = "Content Block",
        description = "Flexible content block for article composition",
        oneOf = {
                TextBlockDTO.class,
                HeadingBlockDTO.class,
                ImageBlockDTO.class,
                VideoBlockDTO.class,
                CodeBlockDTO.class,
                QuoteBlockDTO.class,
                EmbedBlockDTO.class,
                DividerBlockDTO.class
        }
)
public class ContentBlockDTO {
    @NotBlank(message = "Block type is required")
    @Pattern(
            regexp = "^(text|heading|image|video|code|quote|embed|divider)$",
            message = "Invalid block type"
    )
    @Schema(
            title = "Block Type",
            example = "text",
            allowableValues = {"text", "heading", "image", "video", "code", "quote", "embed", "divider"}
    )
    protected String type;

}

```

### src/main/java/com/final_project/blog_service/dto/ContentBlockRequest.java

`$lang
package com.final_project.blog_service.dto;


import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Content Block Request",
        description = "A single content block (text, image, video, code, quote, embed, etc.)",
        example = "{\"type\": \"text\", \"data\": {\"text\": \"Your content here\"}}"
)
public class ContentBlockRequest {

    @NotBlank(message = "Block type is required")
    @Pattern(
            regexp = "^(text|heading|image|video|code|quote|embed|divider)$",
            message = "Block type must be one of: text, heading, image, video, code, quote, embed, divider"
    )
    @Schema(
            title = "Block Type",
            description = "The type of content block",
            example = "text",
            allowableValues = {"text", "heading", "image", "video", "code", "quote", "embed", "divider"},
            type = "string"
    )
    private String type;

    @Schema(
            title = "Block Data",
            description = "Block-specific data structure (varies by type)",
            example = "{\"text\": \"This is a text block\"}"
    )
    private JsonNode data;
}
```

### src/main/java/com/final_project/blog_service/dto/ContentBlockResponse.java

`$lang
package com.final_project.blog_service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Content Block Response",
        description = "A single content block in response"
)
public class ContentBlockResponse {

    @Schema(
            title = "Block Type",
            example = "text"
    )
    private String type;

    @Schema(
            title = "Block Data",
            example = "{\"text\": \"Content here\"}"
    )
    private JsonNode data;
}
```

### src/main/java/com/final_project/blog_service/dto/ContentResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Content Response",
        description = "Article content with blocks and metadata"
)
public class ContentResponse {

    @ArraySchema(
            schema = @Schema(implementation = ContentBlockResponse.class),
            minItems = 1
    )
    private List<ContentBlockResponse> blocks;

    @Schema(
            title = "Estimated Read Time",
            description = "Time to read in minutes",
            example = "5"
    )
    private Integer estimatedReadTime;
}
```

### src/main/java/com/final_project/blog_service/dto/CreateArticleRequest.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Create Article Request",
        description = "Request body for creating a new blog article",
        example = "{\"title\": \"My First Blog Post\", \"subtitle\": \"An exciting journey\", \"blocks\": [...], \"category\": \"Technology\", \"tags\": [\"javascript\", \"web-dev\"], \"description\": \"A comprehensive guide\"}"
)
public class CreateArticleRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    @Schema(
            title = "Article Title",
            description = "The main title of the article",
            example = "Getting Started with Spring Boot 3",
            minLength = 3,
            maxLength = 200
    )
    private String title;

    @Size(max = 500, message = "Subtitle must not exceed 500 characters")
    @Schema(
            title = "Article Subtitle",
            description = "A brief subtitle or summary of the article",
            example = "A comprehensive guide to building modern web applications",
            maxLength = 500
    )
    private String subtitle;

    @NotEmpty(message = "Article must have at least one content block")
    @Valid
    @ArraySchema(
            schema = @Schema(implementation = ContentBlockRequest.class),
            minItems = 1,
            arraySchema = @Schema(
                    title = "Content Blocks",
                    description = "Array of content blocks (text, images, videos, code, etc.)"
            )
    )
    private List<ContentBlockRequest> blocks;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    @Schema(
            title = "Article Category",
            description = "The category or topic of the article",
            example = "Technology",
            maxLength = 100
    )
    private String category;

    @Schema(
            title = "Article Tags",
            description = "List of tags for categorization and search",
            example = "[\"javascript\", \"web-development\", \"spring-boot\"]"

    )
    private List<String> tags;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(
            title = "Article Description",
            description = "SEO meta description for the article",
            example = "Learn how to build modern web applications using Spring Boot 3 and MongoDB",
            maxLength = 500
    )
    private String description;
}
```

### src/main/java/com/final_project/blog_service/dto/CreateArticleWithFilesRequest.java

`$lang
package com.final_project.blog_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * Create Article with Files Request
 *
 * Supports flexible content blocks including images and videos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Create Article with Files Request",
        description = "Request to create article with flexible content blocks (text, images, videos, etc.)",
        example = """
        {
          "title": "My Article with Images",
          "subtitle": "A complete guide",
          "blocks": [
            {
              "type": "heading",
              "level": 2,
              "text": "Introduction"
            },
            {
              "type": "text",
              "text": "This is an introduction paragraph"
            },
            {
              "type": "image",
              "fileId": "image_12345",
              "fileUrl": "https://cdn.example.com/files/image_12345.jpg",
              "alt": "Screenshot",
              "caption": "Figure 1"
            },
            {
              "type": "code",
              "language": "javascript",
              "code": "const x = 1;"
            },
            {
              "type": "video",
              "fileId": "video_12345",
              "fileUrl": "https://cdn.example.com/files/video_12345.mp4",
              "duration": 120
            }
          ],
          "category": "Technology",
          "tags": ["javascript", "tutorial"]
        }
        """
)
public class CreateArticleWithFilesRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    @Schema(
            title = "Article Title",
            example = "My Article with Images and Videos"
    )
    private String title;

    @Size(max = 500, message = "Subtitle must not exceed 500 characters")
    @Schema(
            title = "Article Subtitle",
            example = "A comprehensive guide with rich media"
    )
    private String subtitle;

    @NotEmpty(message = "Article must have at least one content block")
    @Valid
    @Schema(
            title = "Content Blocks",
            description = "Flexible array of content blocks (text, images, videos, code, quotes, embeds, etc.)",
            type = "array"
    )
    private List<FlexibleContentBlockRequest> blocks;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    @Schema(
            title = "Category",
            example = "Technology"
    )
    private String category;

    @Schema(
            title = "Tags",
            example = "[\"javascript\", \"tutorial\", \"web-development\"]"
    )
    private List<String> tags;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(
            title = "Description",
            example = "SEO meta description for the article"
    )
    private String description;

    @Schema(
            title = "Cover Image URL",
            description = "URL to cover image from file upload",
            example = "https://cdn.example.com/files/cover_12345.jpg"
    )
    private String coverImageUrl;
}
```

### src/main/java/com/final_project/blog_service/dto/CreateCommentRequest.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Create Comment Request",
        description = "Request to post a comment on an article"
)
public class CreateCommentRequest {

    @NotBlank(message = "Comment body is required")
    @Size(min = 1, max = 5000, message = "Comment must be between 1 and 5000 characters")
    @Schema(
            title = "Comment Body",
            description = "The text content of the comment",
            example = "Great article! Very informative.",
            minLength = 1,
            maxLength = 5000
    )
    private String body;
}
```

### src/main/java/com/final_project/blog_service/dto/DividerBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Divider Block - Visual separator
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Divider Block",
        description = "Visual divider/separator",
        example = "{\"type\": \"divider\", \"data\": {}}"
)
public class DividerBlockDTO extends ContentBlockDTO {

    public DividerBlockDTO(String type) {
        this.type = type;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/EmbedBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Embed Block - External embeds (YouTube, Twitter, etc.)
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Embed Block",
        description = "External content embed",
        example = "{\"type\": \"embed\", \"data\": {\"provider\": \"youtube\", \"embedUrl\": \"https://www.youtube.com/embed/dQw4w9WgXcQ\"}}"
)
public class EmbedBlockDTO extends ContentBlockDTO {

    @NotBlank(message = "Provider is required")
    @Pattern(
            regexp = "^(youtube|twitter|vimeo|codepen|gist|instagram)$",
            message = "Invalid provider"
    )
    @Schema(
            title = "Provider",
            description = "Embed service provider",
            example = "youtube",
            allowableValues = {"youtube", "twitter", "vimeo", "codepen", "gist", "instagram"}
    )
    private String provider;

    @NotBlank(message = "Embed URL is required")
    @Schema(
            title = "Embed URL",
            description = "URL to embed content",
            example = "https://www.youtube.com/embed/dQw4w9WgXcQ"
    )
    private String embedUrl;

    @Size(max = 500, message = "Title must not exceed 500 characters")
    @Schema(
            title = "Title",
            description = "Embed title",
            example = "Tutorial video"
    )
    private String title;

    public EmbedBlockDTO(String type, String provider, String embedUrl) {
        this.type = type;
        this.provider = provider;
        this.embedUrl = embedUrl;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/ErrorResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Error Response",
        description = "Standard error response format"
)
public class ErrorResponse {

    @Schema(
            title = "Error Code",
            description = "Error type/code",
            example = "RESOURCE_NOT_FOUND"
    )
    private String error;

    @Schema(
            title = "Error Message",
            description = "Human-readable error message",
            example = "Article not found with ID: article_123"
    )
    private String message;

    @Schema(
            title = "Timestamp",
            description = "When the error occurred",
            example = "2024-01-15T10:50:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime timestamp;

    @Schema(
            title = "Request Path",
            description = "The API path that caused the error",
            example = "/api/v1/articles/article_123"
    )
    private String path;

    @Schema(
            title = "HTTP Status Code",
            description = "HTTP status code",
            example = "404"
    )
    private Integer status;

    @Schema(
            title = "Details",
            description = "Additional error details (for validation errors)",
            example = "{\"title\": \"Title is required\"}",
            nullable = true
    )
    private Object details;
}
```

### src/main/java/com/final_project/blog_service/dto/FileCdnUrlResponse.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * File CDN URL Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(
        title = "File CDN URL Response",
        description = "CDN URL and metadata for a file"
)
public class FileCdnUrlResponse {

    @Schema(title = "File ID")
    private String fileId;

    @Schema(
            title = "CDN URL",
            description = "Public CDN URL to access the file"
    )
    private String cdnUrl;

    @Schema(
            title = "Thumbnail URL",
            description = "Thumbnail URL (if available)"
    )
    private String thumbnailUrl;

    @Schema(
            title = "URL Expiration",
            description = "When the URL expires (if applicable)",
            example = "2024-01-15T10:30:00"
    )
    private LocalDateTime expiresAt;
}
```

### src/main/java/com/final_project/blog_service/dto/FileMetadataResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(
        title = "File Metadata Response",
        description = "Metadata information about a file"
)
public class FileMetadataResponse {

    @Schema(title = "File ID")
    private String fileId;

    @Schema(title = "Original Filename")
    private String originalFilename;

    @Schema(title = "File Size")
    private Long fileSize;

    @Schema(title = "MIME Type")
    private String mimeType;

    @Schema(title = "CDN URL")
    private String cdnUrl;

    @Schema(title = "Thumbnail URL")
    private String thumbnailUrl;

    @Schema(title = "Upload Timestamp")
    private LocalDateTime uploadedAt;

    @Schema(title = "Image Width")
    private Integer imageWidth;

    @Schema(title = "Image Height")
    private Integer imageHeight;

    @Schema(title = "Video Duration")
    private Integer videoDurationSeconds;
}
```

### src/main/java/com/final_project/blog_service/dto/FileUploadResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * File Upload Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(
        title = "File Upload Response",
        description = "Response after successfully uploading a file"
)
public class FileUploadResponse {

    @Schema(
            title = "File ID",
            description = "Unique identifier for the uploaded file",
            example = "file_12345"
    )
    private String fileId;

    @Schema(
            title = "Original Filename",
            example = "article-cover.jpg"
    )
    private String originalFilename;

    @Schema(
            title = "File Size",
            description = "Size in bytes",
            example = "2048576"
    )
    private Long fileSize;

    @Schema(
            title = "MIME Type",
            example = "image/jpeg"
    )
    private String mimeType;

    @Schema(
            title = "CDN URL",
            description = "Public CDN URL to access the file",
            example = "https://cdn.example.com/files/file_12345.jpg"
    )
    private String cdnUrl;

    @Schema(
            title = "File Type",
            description = "Type of file: image, video, document",
            example = "image",
            allowableValues = {"image", "video", "document"}
    )
    private String fileType;

    @Schema(
            title = "Upload Timestamp",
            example = "2024-01-15T10:30:00"
    )
    private LocalDateTime uploadedAt;

    // For images
    @Schema(
            title = "Image Width",
            description = "Only for image files",
            example = "1920"
    )
    private Integer imageWidth;

    @Schema(
            title = "Image Height",
            description = "Only for image files",
            example = "1080"
    )
    private Integer imageHeight;

    @Schema(
            title = "Thumbnail URL",
            description = "URL to thumbnail image (if available)",
            example = "https://cdn.example.com/files/file_12345_thumb.jpg"
    )
    private String thumbnailUrl;

    // For videos
    @Schema(
            title = "Video Duration",
            description = "Duration in seconds (only for video files)",
            example = "120"
    )
    private Integer videoDurationSeconds;

    @Schema(
            title = "Processing Status",
            description = "Video processing status",
            example = "COMPLETED",
            allowableValues = {"PENDING", "PROCESSING", "COMPLETED", "FAILED"}
    )
    private String processingStatus;
}
```

### src/main/java/com/final_project/blog_service/dto/FlexibleContentBlockRequest.java

`$lang
package com.final_project.blog_service.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Flexible Content Block Request
 *
 * Polymorphic JSON structure supporting multiple block types
 * Each block type has specific required/optional fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Flexible Content Block",
        description = "Flexible content block supporting multiple types with block-specific data"
)
public class FlexibleContentBlockRequest {
    @NotBlank(message = "Block type is required")
    @Pattern(
            regexp = "^(text|heading|image|video|code|quote|embed|divider)$",
            message = "Block type must be: text, heading, image, video, code, quote, embed, or divider"
    )
    @Schema(
            title = "Block Type",
            description = "Type of content block",
            example = "text",
            allowableValues = {"text", "heading", "image", "video", "code", "quote", "embed", "divider"}
    )
    private String type;

    // Text Block Fields
    @Schema(
            title = "Text Content (for text blocks)",
            example = "Paragraph content"
    )
    private String text;

    // Heading Block Fields
    @Schema(
            title = "Heading Level (for heading blocks)",
            example = "2",
            minimum = "1",
            maximum = "6"
    )
    private Integer level;

    // Image Block Fields
    @Schema(
            title = "File ID (for image/video blocks)",
            description = "ID returned from file upload endpoint",
            example = "image_12345"
    )
    private String fileId;

    @Schema(
            title = "File URL (for image/video blocks)",
            description = "CDN URL from file upload response",
            example = "https://cdn.example.com/files/image_12345.jpg"
    )
    private String fileUrl;

    @Schema(
            title = "Alternative Text (for image blocks)",
            example = "Image description"
    )
    private String alt;

    @Schema(
            title = "Caption (for image/video blocks)",
            example = "Figure 1: Example"
    )
    private String caption;

    @Schema(
            title = "Thumbnail URL",
            example = "https://cdn.example.com/files/image_12345_thumb.jpg"
    )
    private String thumbnailUrl;

    @Schema(
            title = "Width (for image blocks)",
            example = "1920"
    )
    private Integer width;

    @Schema(
            title = "Height (for image blocks)",
            example = "1080"
    )
    private Integer height;

    @Schema(
            title = "Duration (for video blocks, in seconds)",
            example = "120"
    )
    private Integer duration;

    @Schema(
            title = "Title (for video/embed blocks)",
            example = "Tutorial"
    )
    private String title;

    @Schema(
            title = "Description (for video blocks)",
            example = "A tutorial on..."
    )
    private String description;

    // Code Block Fields
    @Schema(
            title = "Code Content (for code blocks)",
            example = "const x = 1;"
    )
    private String code;

    @Schema(
            title = "Programming Language (for code blocks)",
            example = "javascript",
            allowableValues = {
                    "javascript", "python", "java", "cpp", "csharp", "go", "rust",
                    "ruby", "php", "swift", "kotlin", "typescript", "sql", "html",
                    "css", "bash", "shell", "plaintext"
            }
    )
    private String language;

    @Schema(
            title = "Show Line Numbers (for code blocks)",
            example = "true"
    )
    private Boolean showLineNumbers;

    // Quote Block Fields
    @Schema(
            title = "Attribution (for quote blocks)",
            example = "Steve Jobs"
    )
    private String attribution;

    // Embed Block Fields
    @Schema(
            title = "Provider (for embed blocks)",
            example = "youtube",
            allowableValues = {"youtube", "twitter", "vimeo", "codepen", "gist", "instagram"}
    )
    private String provider;

    @Schema(
            title = "Embed URL (for embed blocks)",
            example = "https://www.youtube.com/embed/dQw4w9WgXcQ"
    )
    private String embedUrl;
}
```

### src/main/java/com/final_project/blog_service/dto/HeadingBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Heading Block - Section headers
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Heading Block",
        description = "Section heading (h1-h6)",
        example = "{\"type\": \"heading\", \"data\": {\"level\": 2, \"text\": \"Section Title\"}}"
)
public class HeadingBlockDTO extends ContentBlockDTO {

    @NotNull(message = "Heading level is required")
    @Min(value = 1, message = "Heading level must be 1-6")
    @Max(value = 6, message = "Heading level must be 1-6")
    @Schema(
            title = "Heading Level",
            description = "h1 to h6",
            example = "2",
            minimum = "1",
            maximum = "6"
    )
    private Integer level;

    @NotBlank(message = "Heading text is required")
    @Size(max = 500, message = "Heading must not exceed 500 characters")
    @Schema(
            title = "Heading Text",
            example = "Section Title"
    )
    private String text;

    public HeadingBlockDTO(String type, Integer level, String text) {
        this.type = type;
        this.level = level;
        this.text = text;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/ImageBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Image Block - Images with metadata
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Image Block",
        description = "Image content with metadata",
        example = "{\"type\": \"image\", \"data\": {\"fileId\": \"image_123\", \"fileUrl\": \"https://cdn.../image.jpg\", \"alt\": \"Description\"}}"
)
public class ImageBlockDTO extends ContentBlockDTO {

    @NotBlank(message = "File ID is required")
    @Schema(
            title = "File ID",
            description = "ID returned from file upload endpoint",
            example = "image_12345"
    )
    private String fileId;

    @NotBlank(message = "File URL is required")
    @Schema(
            title = "File URL",
            description = "CDN URL from file upload response",
            example = "https://cdn.example.com/files/image_12345.jpg"
    )
    private String fileUrl;

    @Size(max = 500, message = "Alt text must not exceed 500 characters")
    @Schema(
            title = "Alt Text",
            description = "Alternative text for accessibility",
            example = "Article cover image"
    )
    private String alt;

    @Size(max = 1000, message = "Caption must not exceed 1000 characters")
    @Schema(
            title = "Caption",
            description = "Image caption",
            example = "Figure 1: Example image"
    )
    private String caption;

    @Schema(
            title = "Thumbnail URL",
            description = "URL to thumbnail version",
            example = "https://cdn.example.com/files/image_12345_thumb.jpg"
    )
    private String thumbnailUrl;

    @Min(value = 1, message = "Width must be positive")
    @Schema(
            title = "Width",
            description = "Image width in pixels",
            example = "1920",
            minimum = "1"
    )
    private Integer width;

    @Min(value = 1, message = "Height must be positive")
    @Schema(
            title = "Height",
            description = "Image height in pixels",
            example = "1080",
            minimum = "1"
    )
    private Integer height;

    public ImageBlockDTO(String type, String fileId, String fileUrl, String alt) {
        this.type = type;
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.alt = alt;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/ImageUploadResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Image Upload Response",
        description = "Response after uploading an image"
)
public class ImageUploadResponse {

    @Schema(
            title = "File ID",
            description = "Unique identifier for the image",
            example = "image_12345"
    )
    private String fileId;

    @Schema(
            title = "File URL",
            description = "CDN URL to access the image",
            example = "https://cdn.example.com/files/image_12345.jpg"
    )
    private String fileUrl;

    @Schema(
            title = "Thumbnail URL",
            description = "URL to thumbnail version",
            example = "https://cdn.example.com/files/image_12345_thumb.jpg"
    )
    private String thumbnailUrl;

    @Schema(
            title = "Width",
            description = "Image width in pixels",
            example = "1920"
    )
    private Integer width;

    @Schema(
            title = "Height",
            description = "Image height in pixels",
            example = "1080"
    )
    private Integer height;

    @Schema(
            title = "Alt Text",
            description = "Alternative text for accessibility",
            example = "Article cover image"
    )
    private String alt;

    @Schema(
            title = "Caption",
            description = "Image caption"
    )
    private String caption;

    @Schema(
            title = "MIME Type",
            example = "image/jpeg"
    )
    private String mimeType;

    @Schema(
            title = "File Size",
            description = "Size in bytes",
            example = "2048576"
    )
    private Long fileSize;

    @Schema(
            title = "Uploaded At",
            example = "2024-01-15T10:30:00"
    )
    private LocalDateTime uploadedAt;
}
```

### src/main/java/com/final_project/blog_service/dto/LikeResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Like Response",
        description = "Response after liking an article"
)
public class LikeResponse {

    @Schema(
            title = "Like ID",
            example = "like_123"
    )
    private String id;

    @Schema(
            title = "User ID",
            description = "Who liked the article",
            example = "user_123"
    )
    private String userId;

    @Schema(
            title = "Article ID",
            description = "Which article was liked",
            example = "article_123"
    )
    private String articleId;

    @Schema(
            title = "Created At",
            example = "2024-01-15T10:40:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;
}
```

### src/main/java/com/final_project/blog_service/dto/MetadataResponse.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Metadata Response",
        description = "Article metadata including tags, category, and SEO info"
)
public class MetadataResponse {

    @ArraySchema(
            schema = @Schema(
                    title = "Tag",
                    example = "javascript",
                    type = "string"
            )
    )
    private List<String> tags;

    @Schema(
            title = "Category",
            example = "Technology"
    )
    private String category;

    @Schema(
            title = "Description",
            description = "SEO meta description"
    )
    private String description;

    @ArraySchema(
            schema = @Schema(
                    title = "Keyword",
                    example = "spring-boot",
                    type = "string"
            )
    )
    private List<String> keywords;

    @Schema(
            title = "Cover Image URL",
            example = "https://cdn.example.com/cover.jpg"
    )
    private String coverImageUrl;
}
```

### src/main/java/com/final_project/blog_service/dto/PaginatedResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Paginated Response",
        description = "Generic paginated response wrapper"
)
public class PaginatedResponse<T> {

    @ArraySchema(
            schema = @Schema(implementation = Object.class),
            arraySchema = @Schema(
                    title = "Data",
                    description = "Array of items on this page"
            )
    )
    private List<T> data;

    @Schema(
            title = "Pagination",
            description = "Pagination metadata"
    )
    private PaginationMetadata pagination;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(
            title = "Pagination Metadata",
            description = "Metadata about pagination"
    )
    public static class PaginationMetadata {

        @Schema(
                title = "Current Page",
                description = "Zero-indexed page number",
                example = "0"
        )
        private Integer page;

        @Schema(
                title = "Page Size",
                description = "Number of items per page",
                example = "20"
        )
        private Integer pageSize;

        @Schema(
                title = "Total Count",
                description = "Total number of items across all pages",
                example = "150"
        )
        private Long totalCount;

        @Schema(
                title = "Total Pages",
                description = "Total number of pages",
                example = "8"
        )
        private Integer totalPages;

        @Schema(
                title = "Has Next",
                description = "Whether there are more pages",
                example = "true"
        )
        private Boolean hasNext;

        @Schema(
                title = "Has Previous",
                description = "Whether there are previous pages",
                example = "false"
        )
        private Boolean hasPrevious;

        @Schema(
                title = "Next Cursor",
                description = "Cursor for next page (if using cursor pagination)",
                example = "article_150",
                nullable = true
        )
        private String nextCursor;

        @Schema(
                title = "Previous Cursor",
                description = "Cursor for previous page",
                example = "article_20",
                nullable = true
        )
        private String previousCursor;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/PublishArticleRequest.java

`$lang
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.ArticleVisiblity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Publish Article Request",
        description = "Request to publish an article"
)
public class PublishArticleRequest {

    @NotBlank(message = "Visibility is required")
    @Pattern(
            regexp = "^(PUBLIC|PRIVATE|UNLISTED)$",
            message = "Visibility must be one of: PUBLIC, PRIVATE, UNLISTED"
    )
    @Schema(
            title = "Visibility",
            description = "Who can see this article",
            example = "PUBLIC",
            allowableValues = {"PUBLIC", "PRIVATE", "UNLISTED"}
    )
    private ArticleVisiblity visibility;
}
```

### src/main/java/com/final_project/blog_service/dto/QuoteBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Quote Block - Quoted text with attribution
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Quote Block",
        description = "Quoted text with optional attribution",
        example = "{\"type\": \"quote\", \"data\": {\"text\": \"Life is 10% what happens and 90% how you react\", \"attribution\": \"Charles Swindoll\"}}"
)
public class QuoteBlockDTO extends ContentBlockDTO {

    @NotBlank(message = "Quote text is required")
    @Size(max = 2000, message = "Quote must not exceed 2000 characters")
    @Schema(
            title = "Quote Text",
            example = "Life is 10% what happens to you and 90% how you react to it"
    )
    private String text;

    @Size(max = 500, message = "Attribution must not exceed 500 characters")
    @Schema(
            title = "Attribution",
            description = "Quote author or source",
            example = "Charles R. Swindoll"
    )
    private String attribution;

    public QuoteBlockDTO(String type, String text, String attribution) {
        this.type = type;
        this.text = text;
        this.attribution = attribution;
    }
}
```

### src/main/java/com/final_project/blog_service/dto/SearchArticleRequest.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Search Article Request",
        description = "Request for full-text search on articles"
)
public class SearchArticleRequest {

    @NotBlank(message = "Search query is required")
    @Schema(
            title = "Query",
            description = "The search query string",
            example = "spring boot mongodb",
            minLength = 1
    )
    private String query;

    @Schema(
            title = "Tags",
            description = "Filter by specific tags",
            example = "[\"java\", \"spring\"]"
    )
    private List<String> tags;

    @Schema(
            title = "Category",
            description = "Filter by category",
            example = "Technology"
    )
    private String category;

    @Min(value = 0, message = "Page must be >= 0")
    @Schema(
            title = "Page",
            description = "Page number (zero-indexed)",
            example = "0",
            minimum = "0"
    )
    private Integer page;

    @Min(value = 1, message = "Page size must be >= 1")
    @Max(value = 100, message = "Page size must be <= 100")
    @Builder.Default
    @Schema(
            title = "Page Size",
            description = "Number of results per page",
            example = "20",
            minimum = "1",
            maximum = "100"
    )
    private Integer pageSize = 20;

    @Pattern(
            regexp = "^(relevance|recent|trending)$",
            message = "Sort by must be one of: relevance, recent, trending"
    )
    @Builder.Default
    @Schema(
            title = "Sort By",
            description = "Sort order for results",
            example = "relevance",
            allowableValues = {"relevance", "recent", "trending"}
    )
    private String sortBy = "relevance";
}
```

### src/main/java/com/final_project/blog_service/dto/ShareRequest.java

`$lang
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.SharedPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Share Request",
        description = "Request to track sharing of an article"
)
public class ShareRequest {

    @NotBlank(message = "Platform is required")
    @Pattern(
            regexp = "^(TWITTER|FACEBOOK|LINKEDIN|COPY_LINK|EMAIL)$",
            message = "Platform must be one of: TWITTER, FACEBOOK, LINKEDIN, COPY_LINK, EMAIL"
    )
    @Schema(
            title = "Platform",
            description = "The platform where the article was shared",
            example = "TWITTER",
            allowableValues = {"TWITTER", "FACEBOOK", "LINKEDIN", "COPY_LINK", "EMAIL"}
    )
    private SharedPlatform platform;

    @Size(max = 500, message = "Custom message must not exceed 500 characters")
    @Schema(
            title = "Custom Message",
            description = "Optional custom message when sharing",
            example = "Check out this amazing article!",
            maxLength = 500
    )
    private String customMessage;
}
```

### src/main/java/com/final_project/blog_service/dto/ShareResponse.java

`$lang
package com.final_project.blog_service.dto;


import com.final_project.blog_service.model.SharedPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Share Response",
        description = "Response after sharing an article"
)
public class ShareResponse {

    @Schema(
            title = "Share ID",
            example = "share_123"
    )
    private String id;

    @Schema(
            title = "User ID",
            description = "Who shared the article",
            example = "user_123"
    )
    private String userId;

    @Schema(
            title = "Article ID",
            description = "Which article was shared",
            example = "article_123"
    )
    private String articleId;

    @Schema(
            title = "Platform",
            example = "TWITTER"
    )
    private SharedPlatform platform;

    @Schema(
            title = "Created At",
            example = "2024-01-15T10:45:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;
}
```

### src/main/java/com/final_project/blog_service/dto/StatsResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Statistics Response",
        description = "Engagement and view statistics for an article"
)
public class StatsResponse {

    @Schema(
            title = "Views",
            description = "Total page views",
            example = "234",
            minimum = "0"
    )
    private Long views;

    @Schema(
            title = "Reads",
            description = "Engaged reads (2+ minutes on page)",
            example = "189",
            minimum = "0"
    )
    private Long reads;

    @Schema(
            title = "Likes",
            description = "Number of likes/hearts",
            example = "45",
            minimum = "0"
    )
    private Long likes;

    @Schema(
            title = "Comment Count",
            description = "Total number of comments",
            example = "12",
            minimum = "0"
    )
    private Long commentCount;

    @Schema(
            title = "Share Count",
            description = "Total shares across all platforms",
            example = "8",
            minimum = "0"
    )
    private Long shareCount;

    @Schema(
            title = "Last Engaged At",
            description = "Timestamp of last engagement (like, comment, share)",
            example = "2024-01-15T15:45:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime lastEngagedAt;
}
```

### src/main/java/com/final_project/blog_service/dto/SuccessResponse.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Success Response",
        description = "Generic success response wrapper"
)
public class SuccessResponse<T> {

    @Schema(
            title = "Status",
            description = "Response status",
            example = "success"
    )
    private String status;

    @Schema(
            title = "Message",
            description = "Optional success message",
            example = "Article created successfully"
    )
    private String message;

    @Schema(
            title = "Data",
            description = "Response data"
    )
    private T data;

    @Schema(
            title = "Timestamp",
            example = "2024-01-15T10:30:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime timestamp;
}
```

### src/main/java/com/final_project/blog_service/dto/TextBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * Text Block - Simple text content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Text Block",
        description = "Rich text content block",
        example = "{\"type\": \"text\", \"data\": {\"text\": \"Paragraph text here\"}}"
)
public class TextBlockDTO extends ContentBlockDTO {

    @NotBlank(message = "Text content is required")
    @Size(max = 10000, message = "Text must not exceed 10000 characters")
    @Schema(
            title = "Text Content",
            description = "The text content",
            example = "This is a paragraph of article content"
    )
    private String text;

    public TextBlockDTO(String type, String text) {
        this.type = type;
        this.text = text;
    }
}





```

### src/main/java/com/final_project/blog_service/dto/UpdateArticleRequest.java

`$lang
package com.final_project.blog_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

/**
 * ============= CREATE/UPDATE REQUESTS =============
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Update Article Request",
        description = "Request body for updating an existing blog article"
)
public class UpdateArticleRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    @Schema(
            title = "Article Title",
            example = "Updated Title",
            minLength = 3,
            maxLength = 200
    )
    private String title;

    @Size(max = 500, message = "Subtitle must not exceed 500 characters")
    @Schema(
            title = "Article Subtitle",
            example = "Updated subtitle",
            maxLength = 500
    )
    private String subtitle;

    @NotEmpty(message = "Article must have at least one content block")
    @Valid
    @ArraySchema(
            schema = @Schema(implementation = ContentBlockRequest.class),
            minItems = 1
    )
    private List<ContentBlockRequest> blocks;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    @Schema(
            title = "Article Category",
            example = "Technology",
            maxLength = 100
    )
    private String category;

    @Schema(
            title = "Article Tags",
            example = "[\"javascript\", \"web-dev\"]"
    )
    private List<String> tags;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(
            title = "Article Description",
            example = "Updated description",
            maxLength = 500
    )
    private String description;

    @Schema(
            title = "Cover Image URL",
            description = "URL of the cover image from File Service",
            example = "https://cdn.example.com/images/article-cover.jpg",
            format = "url"
    )
    private String coverImageUrl;
}


/**
 * ============= RESPONSE DTOs =============
 */









/**
 * ============= COMMENT DTOs =============
 */






/**
 * ============= ENGAGEMENT DTOs =============
 */




/**
 * ============= PAGINATION RESPONSE =============
 */


/**
 * ============= SEARCH DTOs =============
 */


/**
 * ============= ERROR RESPONSE =============
 */



/**
 * ============= SUCCESS RESPONSE =============
 */
```

### src/main/java/com/final_project/blog_service/dto/UserAuthorResponse.java

`$lang
package com.final_project.blog_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAuthorResponse  {
    private String id;
    private String userName;
    private String email;
    private String profile;
    private String entityId;
    private String userType;
}
```

### src/main/java/com/final_project/blog_service/dto/UserExistsResponse.java

`$lang
package com.final_project.blog_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserExistsResponse {
    private String userId;
    private boolean exists;
}
```

### src/main/java/com/final_project/blog_service/dto/UserPreferencesResponse.java

`$lang
package com.final_project.blog_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferencesResponse {
    private String userId;
    private Boolean emailNotifications;
    private Boolean allowComments;
    private String defaultPublishStatus;
}
```

### src/main/java/com/final_project/blog_service/dto/UserProfileResponse.java

`$lang
package com.final_project.blog_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class UserProfileResponse {
    private String id;
    private String username;
    private String email;
    private String displayName;
    private String profileImageUrl;
    private String bio;
    private Long totalArticles;
    private LocalDateTime createdAt;
}
```

### src/main/java/com/final_project/blog_service/dto/VideoBlockDTO.java

`$lang
package com.final_project.blog_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Video Block - Videos with metadata
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Schema(
        title = "Video Block",
        description = "Video content with metadata",
        example = "{\"type\": \"video\", \"data\": {\"fileId\": \"video_123\", \"fileUrl\": \"https://cdn.../video.mp4\", \"duration\": 120}}"
)
public class VideoBlockDTO extends ContentBlockDTO {

    @NotBlank(message = "File ID is required")
    @Schema(
            title = "File ID",
            description = "ID returned from file upload endpoint",
            example = "video_12345"
    )
    private String fileId;

    @NotBlank(message = "File URL is required")
    @Schema(
            title = "File URL",
            description = "CDN URL from file upload response",
            example = "https://cdn.example.com/files/video_12345.mp4"
    )
    private String fileUrl;

    @Schema(
            title = "Thumbnail URL",
            description = "URL to video thumbnail",
            example = "https://cdn.example.com/files/video_12345_thumb.jpg"
    )
    private String thumbnailUrl;

    @Min(value = 1, message = "Duration must be positive")
    @Schema(
            title = "Duration",
            description = "Video duration in seconds",
            example = "120",
            minimum = "1"
    )
    private Integer duration;

    @Size(max = 500, message = "Title must not exceed 500 characters")
    @Schema(
            title = "Title",
            description = "Video title",
            example = "Tutorial video"
    )
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Schema(
            title = "Description",
            description = "Video description",
            example = "A tutorial on how to do something"
    )
    private String description;

    public VideoBlockDTO(String type, String fileId, String fileUrl, Integer duration) {
        super();
        this.type = type;
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.duration = duration;
    }
}

```

### src/main/java/com/final_project/blog_service/dto/VideoUploadResponse.java

`$lang
package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Video Upload Response",
        description = "Response after uploading a video"
)
public class VideoUploadResponse {

    @Schema(
            title = "File ID",
            example = "video_12345"
    )
    private String fileId;

    @Schema(
            title = "File URL",
            description = "CDN URL to access the video",
            example = "https://cdn.example.com/files/video_12345.mp4"
    )
    private String fileUrl;

    @Schema(
            title = "Thumbnail URL",
            description = "URL to video thumbnail",
            example = "https://cdn.example.com/files/video_12345_thumb.jpg"
    )
    private String thumbnailUrl;

    @Schema(
            title = "Duration",
            description = "Video duration in seconds",
            example = "120"
    )
    private Integer duration;

    @Schema(
            title = "Title",
            description = "Video title"
    )
    private String title;

    @Schema(
            title = "Description",
            description = "Video description"
    )
    private String description;

    @Schema(
            title = "MIME Type",
            example = "video/mp4"
    )
    private String mimeType;

    @Schema(
            title = "File Size",
            description = "Size in bytes",
            example = "52428800"
    )
    private Long fileSize;

    @Schema(
            title = "Processing Status",
            description = "Video processing status",
            example = "PROCESSING",
            allowableValues = {"PENDING", "PROCESSING", "COMPLETED", "FAILED"}
    )
    private String processingStatus;

    @Schema(
            title = "Uploaded At",
            example = "2024-01-15T10:30:00"
    )
    private LocalDateTime uploadedAt;
}
```

### src/main/java/com/final_project/blog_service/exception/FileNotFound.java

`$lang
package com.final_project.blog_service.exception;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String message) {
        super(message);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/FileServiceException.java

`$lang
package com.final_project.blog_service.exception;

public class FileServiceException extends RuntimeException {
    public FileServiceException(String message) {
        super(message);
    }

    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/FileUploadException.java

`$lang
package com.final_project.blog_service.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/GlobalExceptionHandler.java

`$lang
package com.final_project.blog_service.exception;


import com.final_project.blog_service.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request
    ) {
        log.warn("Resource not found: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .error("RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            WebRequest request
    ) {
        log.warn("Unauthorized access: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .error("UNAUTHORIZED")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(
            IllegalStateException ex,
            WebRequest request
    ) {
        log.warn("Invalid state: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .error("INVALID_STATE")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        log.warn("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("error", "VALIDATION_ERROR");
        response.put("message", "Validation failed");
        response.put("errors", errors);
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request
    ) {
        log.error("Unexpected error: ", ex);

        ErrorResponse error = ErrorResponse.builder()
                .error("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/ResourceNotFoundException.java

`$lang
package com.final_project.blog_service.exception;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/UnauthorizedException.java

`$lang
package com.final_project.blog_service.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/UserNotFoundException.java

`$lang
package com.final_project.blog_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/UserServiceException.java

`$lang
package com.final_project.blog_service.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/exception/UserServiceUnavailableException.java

`$lang
package com.final_project.blog_service.exception;

public class UserServiceUnavailableException extends RuntimeException {
    public UserServiceUnavailableException(String message) {
        super(message);
    }

    public UserServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### src/main/java/com/final_project/blog_service/model/Article.java

`$lang
package com.final_project.blog_service.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "articles")
@CompoundIndexes({
        @CompoundIndex(name = "author_published_idx", def = "{'authorId': 1, 'publishedAt': -1}"),
        @CompoundIndex(name = "status_published_idx", def = "{'status': 1, 'publishedAt': -1}"),
        @CompoundIndex(name = "tags_published_idx", def = "{'metadata.tags': 1, 'publishedAt': -1}")
})
public class Article {

    @Id
    private String id;

    @Indexed(unique = true)
    private String slug;  // URL-friendly identifier

    @Indexed
    private String authorId;

    private String title;

    private String subtitle;

    private Content content;

    private Metadata metadata;

    @Builder.Default
    private ArticleStatus status = ArticleStatus.PUBLISHED;

    @Builder.Default
    private ArticleVisiblity visibility = ArticleVisiblity.PUBLIC;

    private Stats stats;

    private Seo seo;

    @Builder.Default
    private List<EditHistory> editHistory = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;

    private LocalDateTime archivedAt;

    /**
     * Flexible content block structure
     * Supports: text, heading, image, video, code, quote, embed, divider
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Content {

        @Builder.Default
        private List<ContentBlock> blocks = new ArrayList<>();

        @Builder.Default
        private Integer estimatedReadTime = 1;  // in minutes
    }

    /**
     * Individual content block
     * Data field is polymorphic - structure depends on block type
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContentBlock {
        private String type;  // text, heading, image, video, code, quote, embed, divider
        private JsonNode data;  // flexible JSON structure based on type
    }

    /**
     * Block type definitions for reference:
     *
     * TEXT:
     * { type: "text", data: { text: "Your text here" } }
     *
     * HEADING:
     * { type: "heading", data: { level: 1, text: "Heading" } }
     *
     * IMAGE:
     * { type: "image", data: {
     *     fileId: "file_123",
     *     fileUrl: "https://cdn.../image.jpg",
     *     alt: "alt text",
     *     caption: "caption",
     *     width: 800, height: 600
     *   } }
     *
     * VIDEO:
     * { type: "video", data: {
     *     fileId: "video_123",
     *     videoUrl: "https://cdn.../video.mp4",
     *     thumbnailUrl: "https://cdn.../thumb.jpg",
     *     duration: 120
     *   } }
     *
     * CODE:
     * { type: "code", data: {
     *     language: "javascript",
     *     code: "const x = 1;",
     *     showLineNumbers: true
     *   } }
     *
     * QUOTE:
     * { type: "quote", data: {
     *     text: "Quote text",
     *     attribution: "Author name"
     *   } }
     *
     * EMBED:
     * { type: "embed", data: {
     *     provider: "youtube|twitter|codepen|gist",
     *     embedUrl: "https://...",
     *     title: "Embed title"
     *   } }
     *
     * DIVIDER:
     * { type: "divider", data: {} }
     */

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {

        @Builder.Default
        private List<String> tags = new ArrayList<>();

        private String category;

        private String description;  // SEO meta description

        @Builder.Default
        private List<String> keywords = new ArrayList<>();  // SEO keywords

        private String coverImageUrl;  // from file service
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Stats {

        @Builder.Default
        private Long views = 0L;  // page views

        @Builder.Default
        private Long reads = 0L;  // engaged reads (2+ minutes)

        @Builder.Default
        private Long likes = 0L;

        @Builder.Default
        private Long commentCount = 0L;

        @Builder.Default
        private Long shareCount = 0L;

        private LocalDateTime lastEngagedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Seo {
        private String publishedUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditHistory {
        private Integer version;
        private LocalDateTime updatedAt;
        private String editorId;
        private String summary;
    }
}
```

### src/main/java/com/final_project/blog_service/model/ArticleStatus.java

`$lang
package com.final_project.blog_service.model;

public enum ArticleStatus {

    DRAFT("DRAFT"), PUBLISHED("PUBLISHED"), ARCHIVED("ARCHIVED");
    private final String status;
    ArticleStatus(String status) {
        this.status = status.toUpperCase();
    }
    public String getStatus() {
        return this.status;
    }
}
```

### src/main/java/com/final_project/blog_service/model/ArticleVisiblity.java

`$lang
package com.final_project.blog_service.model;

public enum ArticleVisiblity {
    PUBLIC("PUBLIC"), PRIVATE("PRIVATE"), UNLISTED("UNLISTED");
    private final String ArticleVisiblity;
    ArticleVisiblity(String ArticleVisiblity) {
        this.ArticleVisiblity = ArticleVisiblity.toUpperCase();
    }

    public String visiblity(){
        return this.ArticleVisiblity;
    }
}
```

### src/main/java/com/final_project/blog_service/model/Comment.java

`$lang
package com.final_project.blog_service.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import java.time.LocalDateTime;

/**
 * Comment entity representing blog post comments and replies
 * Supports threaded/nested comments (replies to comments)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comments")
@CompoundIndexes({
        @CompoundIndex(name = "article_created_idx", def = "{'articleId': 1, 'createdAt': -1}"),
        @CompoundIndex(name = "parent_created_idx", def = "{'parentCommentId': 1, 'createdAt': -1}")
})
public class Comment {

    @Id
    private String id;

    @Indexed
    private String articleId;  // the article being commented on

    @Indexed
    private String parentCommentId;  // null for top-level comments, objectId for replies

    @Indexed
    private String authorId;

    private Author author;  // denormalized for quick access

    private String body;

    private Engagement engagement;

    @Builder.Default
    private CommentStatus status = CommentStatus.PUBLISHED;  // PUBLISHED, DELETED

    private LocalDateTime editedAt;

    private LocalDateTime createdAt;

    /**
     * Denormalized author info
     * Updated when user profile changes (async event)
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Author {
        private String displayName;
        private String profileImageUrl;
    }

    /**
     * Engagement stats
     * Likes and reply counts for this comment
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Engagement {

        @Builder.Default
        private Long likes = 0L;

        @Builder.Default
        private Long replyCount = 0L;  // number of direct replies to this comment
    }
}
```

### src/main/java/com/final_project/blog_service/model/CommentStatus.java

`$lang
package com.final_project.blog_service.model;

public enum CommentStatus {
    DELETE("DELETE"), PUBLISHED("PUBLISHED"), EDITED("EDITED");
    private final String status;
    CommentStatus( String status){
        this.status = status.toUpperCase();
    }
    public String getStatus(){
        return this.status;
    }
}
```

### src/main/java/com/final_project/blog_service/model/Like.java

`$lang
package com.final_project.blog_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Like entity representing user likes on articles
 * Separate collection for better scalability and independence
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "likes")
@CompoundIndexes({
        @CompoundIndex(name = "user_article_unique_idx", def = "{'userId': 1, 'articleId': 1}", unique = true),
        @CompoundIndex(name = "article_idx", def = "{'articleId': 1, 'createdAt': -1}")
})
public class Like {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String articleId;

    private LocalDateTime createdAt;
}

```

### src/main/java/com/final_project/blog_service/model/ReadingHistory.java

`$lang
package com.final_project.blog_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Reading history entity for tracking user engagement
 * Optional - for future features like recommendations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reading_history")
public class ReadingHistory {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String articleId;

    private LocalDateTime readAt;

    // Time spent reading in seconds
    private Integer timeSpentSeconds;

    // Scroll depth percentage (0-100)
    private Float scrollDepth;

    @Builder.Default
    private Boolean wasRead = false;  // true if spent 2+ minutes
}
```

### src/main/java/com/final_project/blog_service/model/Share.java

`$lang
package com.final_project.blog_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Share entity tracking social shares and sharing activity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "shares")
public class Share {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String articleId;


    private SharedPlatform platform;

    private Metadata metadata;

    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {
        private String customMessage;  // optional message when sharing
        private LocalDateTime timestamp;
    }
}
```

### src/main/java/com/final_project/blog_service/model/SharedPlatform.java

`$lang
package com.final_project.blog_service.model;

public enum SharedPlatform {
    TWITTER("TWITTER"), FACEBOOK("FACEBOOK"), LINKEDIN("LINKEDIN"), COPY_LINK("COPYLINK"), EMAIL("EMAIL");

    private final String platform;
    SharedPlatform( String platform){
        this.platform = platform.toUpperCase();
    }
    public String getPlatform(){
        return this.platform.toUpperCase();
    }
}
```

### src/main/java/com/final_project/blog_service/repo/ArticleRepository.java

`$lang
package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.Article;
import com.final_project.blog_service.model.ArticleStatus;
import com.final_project.blog_service.model.ArticleVisiblity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Article Repository - handles article persistence
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    // Find by slug (for URL routing)
    Optional<Article> findBySlug(String slug);

    // Find published articles by author
    Page<Article> findByAuthorIdAndStatusOrderByPublishedAtDesc(
            String authorId,
            ArticleStatus status,
            Pageable pageable
    );

    // Find all published articles (for feed)
    @Query("{ 'status': 'PUBLISHED', 'visibility': 'PUBLIC' }")
    Page<Article> findPublishedArticles(Pageable pageable);

    // Find articles by tags
    Page<Article> findByMetadataTagsInAndStatusAndVisibility(
            List<String> tags,
            ArticleStatus status,
            ArticleVisiblity visibility,
            Pageable pageable
    );

    // Find articles by category
    Page<Article> findByMetadataCategoryAndStatusAndVisibility(
            String category,
            ArticleStatus status,
            ArticleVisiblity visibility,
            Pageable pageable
    );

    // Find articles by author and status
    List<Article> findByAuthorIdAndStatus(String authorId, ArticleStatus status);

    // Count articles by author
    Long countByAuthorIdAndStatus(String authorId, ArticleStatus status);

    // Find articles published after a date
    List<Article> findByPublishedAtAfterAndStatusOrderByPublishedAtDesc(
            LocalDateTime publishedAt,
            ArticleStatus status
    );

    // Delete soft-deleted articles (archives older than 30 days)
    @Query("{ 'status': 'ARCHIVED', 'archivedAt': { $lt: ?0 } }")
    void deleteOldArchives(LocalDateTime threshold);

    Optional<Article> findArticleByIdAndAuthorId(String articleId, String authorId);
}



```

### src/main/java/com/final_project/blog_service/repo/CommentRepository.java

`$lang
package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.Comment;
import com.final_project.blog_service.model.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comment Repository - handles comment persistence
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    // Get all top-level comments for an article
    Page<Comment> findByArticleIdAndParentCommentIdIsNullAndStatusOrderByCreatedAtDesc(
            String articleId,
            CommentStatus status,
            Pageable pageable
    );

    // Get all replies to a specific comment
    Page<Comment> findByParentCommentIdAndStatusOrderByCreatedAtAsc(
            String parentCommentId,
            CommentStatus status,
            Pageable pageable
    );

    // Count total comments on an article
    Long countByArticleIdAndStatus(String articleId, CommentStatus status);

    // Count replies to a comment
    Long countByParentCommentIdAndStatus(String parentCommentId, CommentStatus status);

    // Get comments by user
    Page<Comment> findByAuthorIdAndStatusOrderByCreatedAtDesc(
            String authorId,
            CommentStatus status,
            Pageable pageable
    );

    // Find deleted comments for cleanup
    List<Comment> findByStatusAndEditedAtBefore(CommentStatus status, LocalDateTime threshold);

    // Check if comment exists
    boolean existsByIdAndStatus(String id, CommentStatus status);
}
```

### src/main/java/com/final_project/blog_service/repo/LikeRepository.java

`$lang
package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Like Repository - handles like persistence
 */
@Repository
public interface LikeRepository extends MongoRepository<Like, String> {

    // Check if user has liked article
    Optional<Like> findByUserIdAndArticleId(String userId, String articleId);

    // Get all likes for an article
    Page<Like> findByArticleIdOrderByCreatedAtDesc(String articleId, Pageable pageable);

    // Get all likes by a user
    Page<Like> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    // Count likes for article
    Long countByArticleId(String articleId);

    // Count likes by user
    Long countByUserId(String userId);

    // Delete like
    void deleteByUserIdAndArticleId(String userId, String articleId);

    // Get user's liked articles (for reading list)
    @Query("{ 'userId': ?0 }")
    Page<Like> getUserLikedArticles(String userId, Pageable pageable);
}
```

### src/main/java/com/final_project/blog_service/repo/ReadingHistoryRepository.java

`$lang
package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.ReadingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ReadingHistory Repository - tracks user reading engagement
 */
@Repository
public interface ReadingHistoryRepository extends MongoRepository<ReadingHistory, String> {

    // Check if user has read article
    Optional<ReadingHistory> findByUserIdAndArticleId(String userId, String articleId);

    // Get user's reading history
    Page<ReadingHistory> findByUserIdOrderByReadAtDesc(String userId, Pageable pageable);

    // Get article's readers
    Page<ReadingHistory> findByArticleIdOrderByReadAtDesc(String articleId, Pageable pageable);

    // Get articles read by user
    @Query("{ 'userId': ?0, 'wasRead': true }")
    List<ReadingHistory> getUserReadArticles(String userId);

    // Count readers
    Long countByArticleId(String articleId);

    // Count articles read by user
    @Query("{ 'userId': ?0, 'wasRead': true }")
    Long countUserReadArticles(String userId);
}
```

### src/main/java/com/final_project/blog_service/repo/ShareRepository.java

`$lang
package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.Share;
import com.final_project.blog_service.model.SharedPlatform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Share Repository - handles share tracking
 */
@Repository
public interface ShareRepository extends MongoRepository<Share, String> {

    // Get all shares for an article
    Page<Share> findByArticleIdOrderByCreatedAtDesc(String articleId, Pageable pageable);

    // Get all shares by a user
    Page<Share> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    // Count total shares for article
    Long countByArticleId(String articleId);

    // Count shares by platform
    Long countByArticleIdAndPlatform(String articleId, SharedPlatform platform);

    // Get shares for article by platform
    List<Share> findByArticleIdAndPlatformOrderByCreatedAtDesc(String articleId, SharedPlatform platform);
}

```

### src/main/java/com/final_project/blog_service/service/ArticleService.java

`$lang
package com.final_project.blog_service.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.blog_service.client.FileServiceClient;
import com.final_project.blog_service.client.UserServiceClient;
import com.final_project.blog_service.exception.ResourceNotFoundException;
import com.final_project.blog_service.exception.UnauthorizedException;
import com.final_project.blog_service.exception.UserNotFoundException;
import com.final_project.blog_service.utile.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.final_project.blog_service.dto.*;
import  com.final_project.blog_service.model.*;
import com.final_project.blog_service.repo.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.final_project.blog_service.utile.ReadTimeCalculator.calculateReadTime;

/**
 * Article Service - Core business logic for article management
 * Handles: create, update, publish, delete articles and comments
 */
@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ShareRepository shareRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final FileServiceClient fileServiceClient;
    private final UserServiceClient userServiceClient;
    private final UserCacheService userCacheService;
    private final ObjectMapper objectMapper;
    public ArticleService(ArticleRepository articleRepository,
                          CommentRepository commentRepository,
                          LikeRepository likeRepository,
                          RedisTemplate<String, String> redisTemplate,
                          FileServiceClient fileServiceClient,
                          UserServiceClient userServiceClient,
                          UserCacheService userCacheService,
                          ObjectMapper objectMapper,
                          ShareRepository shareRepository

    ){
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.fileServiceClient = fileServiceClient;
        this.likeRepository = likeRepository;
        this.objectMapper = objectMapper;
        this.shareRepository = shareRepository;
        this.redisTemplate = redisTemplate;
        this.userServiceClient = userServiceClient;
        this.userCacheService = userCacheService;
    }

    private static final String ARTICLE_CACHE_KEY = "article:";
    private static final String ARTICLE_STATS_CACHE_KEY = "article:stats:";
    private static final int CACHE_TTL_MINUTES = 5;
    /**
     * Create a new draft article
     */
    @Transactional
    public ArticleResponse createArticle(String authorId, CreateArticleRequest request) {
        UserAuthorResponse authorResponse = userServiceClient.getUserAuthor(authorId);
        if (authorResponse == null){
            throw new ResourceNotFoundException("The User is Not Exist");
        }


        Article article = Article.builder()
                .authorId(authorResponse.getId())
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .slug(generateUniqueSlug(request.getTitle()))
                .status(ArticleStatus.DRAFT)
                .visibility(ArticleVisiblity.PUBLIC)
                .content(Article.Content.builder()
                        .blocks(mapContentBlocks(request.getBlocks()))
                        .estimatedReadTime(calculateReadTime(request.getBlocks()))
                        .build()
                )
                .metadata(Article.Metadata.builder()
                        .tags(request.getTags())
                        .category(request.getCategory())
                        .description(request.getDescription())
                        .build()
                )
                .stats(Article.Stats.builder()
                        .views(0L)
                        .reads(0L)
                        .likes(0L)
                        .commentCount(0L)
                        .shareCount(0L)
                        .build()
                )
                .editHistory(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Article saved = articleRepository.save(article);
        return mapToResponse(saved);
    }

    /**
     * Update article (draft or published)
     */
    @Transactional
    public ArticleResponse updateArticle(String articleId, String authorId, UpdateArticleRequest request) {
        Article article = getArticleByIdOrThrow(articleId);
        validateAuthor(article, authorId);

        log.info("Updating article: {}", articleId);

        article.setTitle(request.getTitle());
        article.setSubtitle(request.getSubtitle());
        article.getContent().setBlocks(mapContentBlocks(request.getBlocks()));
        article.getContent().setEstimatedReadTime(calculateReadTime(request.getBlocks()));

        article.getMetadata().setTags(request.getTags());
        article.getMetadata().setCategory(request.getCategory());
        article.getMetadata().setDescription(request.getDescription());
        article.getMetadata().setCoverImageUrl(request.getCoverImageUrl());

        article.setUpdatedAt(LocalDateTime.now());

        // Track edit history
        addEditHistory(article, authorId, "Updated content");

        Article updated = articleRepository.save(article);
        invalidateCache(articleId);

        return mapToResponse(updated);
    }

    /**
     * Publish an article (transition from DRAFT to PUBLISHED)
     */
    @Transactional
    public ArticleResponse publishArticle(String articleId, String authorId, PublishArticleRequest request) {
        Article article = getArticleByIdOrThrow(articleId);
        validateAuthor(article, authorId);

        if (!ArticleStatus.DRAFT.equals(article.getStatus())) {
            throw new IllegalStateException("Only draft articles can be published");
        }

        log.info("Publishing article: {}", articleId);

        article.setStatus(ArticleStatus.PUBLISHED);
        article.setVisibility(request.getVisibility());
        article.setPublishedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        addEditHistory(article, authorId, "Published article");

        Article published = articleRepository.save(article);
        invalidateCache(articleId);

        // TODO: Trigger publish event for notifications
        // publishEvent(new ArticlePublishedEvent(published));

        return mapToResponse(published);
    }

    /**
     * Get article by ID (with cache)
     */
    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(String articleId) {
        String cacheKey = ARTICLE_CACHE_KEY + articleId;

        // Try cache first
        String cachedArticle = redisTemplate.opsForValue().get(cacheKey);
        if (cachedArticle != null) {
            log.debug("Cache hit for article: {}", articleId);
            // Would deserialize here in production
        }

        Article article = getArticleByIdOrThrow(articleId);

        // Cache the result
        redisTemplate.opsForValue().set(cacheKey, article.getId(), CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        // Increment views asynchronously
        incrementViewsAsync(articleId);

        return mapToResponse(article);
    }

    /**
     * Get article by slug (for URL routing)
     */
    @Transactional(readOnly = true)
    public ArticleResponse getArticleBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found: " + slug));

        return mapToResponse(article);
    }

    /**
     * Get user's articles with pagination
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<ArticlePreviewResponse> getUserArticles(String authorId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Article> articles = articleRepository.findByAuthorIdAndStatusOrderByPublishedAtDesc(
                authorId,
                ArticleStatus.PUBLISHED,
                pageable
        );

        return mapToPaginatedResponse(articles, page, pageSize);
    }

    /**
     * Get published articles feed (paginated)
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<ArticlePreviewResponse> getPublishedArticles(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Article> articles = articleRepository.findPublishedArticles(pageable);

        return mapToPaginatedResponse(articles, page, pageSize);
    }

    /**
     * Soft delete article (archive)
     */
    @Transactional
    public void deleteArticle(String articleId, String authorId) {
        Article article = getArticleByIdOrThrow(articleId);
        validateAuthor(article, authorId);

        log.info("Archiving article: {}", articleId);

        article.setStatus(ArticleStatus.ARCHIVED);
        article.setArchivedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(article);
        invalidateCache(articleId);
    }


    /**
     * Post a comment on an article
     */
    @Transactional
    public CommentResponse postComment(String articleId, String authorId, CreateCommentRequest request) {
        Article article = getArticleByIdOrThrow(articleId);

        Comment comment = Comment.builder()
                .articleId(articleId)
                .parentCommentId(null)  // Top-level comment
                .authorId(authorId)
                .body(request.getBody())
                .status(CommentStatus.PUBLISHED)
                .engagement(Comment.Engagement.builder()
                        .likes(0L)
                        .replyCount(0L)
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(comment);

        // Update article comment count
        article.getStats().setCommentCount(article.getStats().getCommentCount() + 1);
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Comment posted on article: {}", articleId);

        return mapCommentToResponse(saved);
    }

    /**
     * Reply to a comment
     */
    @Transactional
    public CommentResponse replyToComment(String articleId, String parentCommentId,
                                          String authorId, CreateCommentRequest request) {
        Article article = getArticleByIdOrThrow(articleId);
        Comment parentComment = getCommentByIdOrThrow(parentCommentId);

        Comment reply = Comment.builder()
                .articleId(articleId)
                .parentCommentId(parentCommentId)
                .authorId(authorId)
                .body(request.getBody())
                .status(CommentStatus.PUBLISHED)
                .engagement(Comment.Engagement.builder()
                        .likes(0L)
                        .replyCount(0L)
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(reply);

        // Update parent comment reply count
        parentComment.getEngagement().setReplyCount(parentComment.getEngagement().getReplyCount() + 1);
        commentRepository.save(parentComment);

        // Update article comment count
        article.getStats().setCommentCount(article.getStats().getCommentCount() + 1);
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Reply posted on comment: {}", parentCommentId);

        return mapCommentToResponse(saved);
    }

    /**
     * Get comments for an article (threaded)
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<CommentThreadResponse> getArticleComments(String articleId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Comment> comments = commentRepository.findByArticleIdAndParentCommentIdIsNullAndStatusOrderByCreatedAtDesc(
                articleId,
                CommentStatus.PUBLISHED,
                pageable
        );

        List<CommentThreadResponse> threads = comments.getContent().stream()
                .map(this::buildCommentThread)
                .toList();

        return PaginatedResponse.<CommentThreadResponse>builder()
                .data(threads)
                .pagination(PaginatedResponse.PaginationMetadata.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .totalCount(comments.getTotalElements())
                        .totalPages(comments.getTotalPages())
                        .hasNext(comments.hasNext())
                        .hasPrevious(comments.hasPrevious())
                        .build()
                )
                .build();
    }

    /**
     * Delete a comment (soft delete)
     */
    @Transactional
    public void deleteComment(String commentId, String authorId) {
        Comment comment = getCommentByIdOrThrow(commentId);

        if (!comment.getAuthorId().equals(authorId)) {
            throw new UnauthorizedException("Not authorized to delete this comment");
        }

        log.info("Deleting comment: {}", commentId);

        comment.setStatus(CommentStatus.DELETE);
        comment.setEditedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    // ============= ENGAGEMENT MANAGEMENT =============

    /**
     * Like an article
     */
    @Transactional
    public LikeResponse likeArticle(String articleId, String userId) {
        Article article = getArticleByIdOrThrow(articleId);

        // Check if already liked
        Optional<Like> existingLike = likeRepository.findByUserIdAndArticleId(userId, articleId);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("Article already liked");
        }

        Like like = Like.builder()
                .userId(userId)
                .articleId(articleId)
                .createdAt(LocalDateTime.now())
                .build();

        Like saved = likeRepository.save(like);

        // Update article like count
        article.getStats().setLikes(article.getStats().getLikes() + 1);
        article.getStats().setLastEngagedAt(LocalDateTime.now());
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Article liked: {}", articleId);

        return mapLikeToResponse(saved);
    }

    /**
     * Unlike an article
     */
    @Transactional
    public void unlikeArticle(String articleId, String userId) {
        Article article = getArticleByIdOrThrow(articleId);

        likeRepository.deleteByUserIdAndArticleId(userId, articleId);

        // Update article like count
        article.getStats().setLikes(Math.max(0, article.getStats().getLikes() - 1));
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Article unliked: {}", articleId);
    }

    /**
     * Track share
     */
    @Transactional
    public ShareResponse shareArticle(String articleId, String userId, ShareRequest request) {
        Article article = getArticleByIdOrThrow(articleId);

        Share share = Share.builder()
                .userId(userId)
                .articleId(articleId)
                .platform(request.getPlatform())
                .metadata(Share.Metadata.builder()
                        .customMessage(request.getCustomMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        Share saved = shareRepository.save(share);

        // Update article share count
        article.getStats().setShareCount(article.getStats().getShareCount() + 1);
        article.getStats().setLastEngagedAt(LocalDateTime.now());
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Article shared: {} on {}", articleId, request.getPlatform());

        return mapShareToResponse(saved);
    }

    // ============= HELPER METHODS =============

    private String generateUniqueSlug(String title) {
        String baseSlug = SlugUtil.generateSlug(title);
        String slug = baseSlug;
        int counter = 1;

        while (articleRepository.findBySlug(slug).isPresent()) {
            slug = baseSlug + "-" + counter++;
        }

        return slug;
    }

    private Integer calculateReadTime(List<ContentBlockRequest> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            return 1;
        }

        int wordCount = blocks.stream()
                .mapToInt(block -> {
                    if ("text".equals(block.getType()) && block.getData().has("text")) {
                        return block.getData().get("text").asText().split("\\s+").length;
                    }
                    return 0;
                })
                .sum();

        // Average reading speed: 200 words per minute
        return Math.max(1, (wordCount + 199) / 200);
    }

    private List<Article.ContentBlock> mapContentBlocks(List<ContentBlockRequest> requests) {
        if (requests == null) {
            return new ArrayList<>();
        }

        return requests.stream()
                .map(req -> Article.ContentBlock.builder()
                        .type(req.getType())
                        .data(req.getData())
                        .build()
                )
                .toList();
    }

    private void addEditHistory(Article article, String editorId, String summary) {
        Article.EditHistory history = new Article.EditHistory();
        history.setVersion((int) (articleRepository.countByAuthorIdAndStatus(article.getAuthorId(), article.getStatus()) + 1));
        history.setUpdatedAt(LocalDateTime.now());
        history.setEditorId(editorId);
        history.setSummary(summary);

        article.getEditHistory().add(history);
    }

    private void incrementViewsAsync(String articleId) {
        // TODO: Implement async increment (use event bus/message queue)
        log.debug("Incrementing views for article: {}", articleId);
    }

    private void validateAuthor(Article article, String userId) {
        if (!article.getAuthorId().equals(userId)) {
            throw new UnauthorizedException("Not authorized to modify this article");
        }
    }

    private void invalidateCache(String articleId) {
        redisTemplate.delete(ARTICLE_CACHE_KEY + articleId);
        redisTemplate.delete(ARTICLE_STATS_CACHE_KEY + articleId);
    }

    private CommentThreadResponse buildCommentThread(Comment comment) {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Comment> replies = commentRepository.findByParentCommentIdAndStatusOrderByCreatedAtAsc(
                comment.getId(),
                CommentStatus.PUBLISHED,
                pageable
        );

        List<CommentThreadResponse> replyThreads = replies.getContent().stream()
                .map(this::buildCommentThread)
                .toList();

        return CommentThreadResponse.builder()
                .comment(mapCommentToResponse(comment))
                .replies(replyThreads)
                .build();
    }

    private Article getArticleByIdOrThrow(String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found: " + id));
    }
    private Comment getCommentByIdOrThrow(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
    }
    private ArticleResponse mapToResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .slug(article.getSlug())
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .content(ContentResponse.builder()
                        .blocks(article.getContent().getBlocks().stream()
                                .map(b -> ContentBlockResponse.builder()
                                        .type(b.getType())
                                        .data(b.getData())
                                        .build()
                                )
                                .toList()
                        )
                        .estimatedReadTime(article.getContent().getEstimatedReadTime())
                        .build()
                )
                .metadata(MetadataResponse.builder()
                        .tags(article.getMetadata().getTags())
                        .category(article.getMetadata().getCategory())
                        .description(article.getMetadata().getDescription())
                        .coverImageUrl(article.getMetadata().getCoverImageUrl())
                        .build()
                )
                .status(article.getStatus())
                .visibility(article.getVisibility())
                .stats(StatsResponse.builder()
                        .views(article.getStats().getViews())
                        .reads(article.getStats().getReads())
                        .likes(article.getStats().getLikes())
                        .commentCount(article.getStats().getCommentCount())
                        .shareCount(article.getStats().getShareCount())
                        .lastEngagedAt(article.getStats().getLastEngagedAt())
                        .build()
                )
                .publishedAt(article.getPublishedAt())
                .updatedAt(article.getUpdatedAt())
                .createdAt(article.getCreatedAt())
                .estimatedReadTime(article.getContent().getEstimatedReadTime())
                .build();
    }
    private PaginatedResponse<ArticlePreviewResponse> mapToPaginatedResponse(Page<Article> page, int pageNum, int pageSize) {
        List<ArticlePreviewResponse> previews = page.getContent().stream()
                .map(this::mapToPreview)
                .toList();
        return PaginatedResponse.<ArticlePreviewResponse>builder()
                .data(previews)
                .pagination(PaginatedResponse.PaginationMetadata.builder()
                        .page(pageNum)
                        .pageSize(pageSize)
                        .totalCount(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .hasNext(page.hasNext())
                        .hasPrevious(page.hasPrevious())
                        .build()
                )
                .build();
    }

    private ArticlePreviewResponse mapToPreview(Article article) {
        return ArticlePreviewResponse.builder()
                .id(article.getId())
                .slug(article.getSlug())
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .coverImageUrl(article.getMetadata().getCoverImageUrl())
                .description(article.getMetadata().getDescription())
                .stats(StatsResponse.builder()
                        .views(article.getStats().getViews())
                        .reads(article.getStats().getReads())
                        .likes(article.getStats().getLikes())
                        .commentCount(article.getStats().getCommentCount())
                        .shareCount(article.getStats().getShareCount())
                        .build()
                )
                .publishedAt(article.getPublishedAt())
                .estimatedReadTime(article.getContent().getEstimatedReadTime())
                .tags(article.getMetadata().getTags())
                .build();
    }

    private CommentResponse mapCommentToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .articleId(comment.getArticleId())
                .parentCommentId(comment.getParentCommentId())
                .body(comment.getBody())
                .engagement(CommentEngagementResponse.builder()
                        .likes(comment.getEngagement().getLikes())
                        .replyCount(comment.getEngagement().getReplyCount())
                        .build()
                )
                .createdAt(comment.getCreatedAt())
                .editedAt(comment.getEditedAt())
                .build();
    }

    private LikeResponse mapLikeToResponse(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .userId(like.getUserId())
                .articleId(like.getArticleId())
                .createdAt(like.getCreatedAt())
                .build();
    }

    private ShareResponse mapShareToResponse(Share share) {
        return ShareResponse.builder()
                .id(share.getId())
                .userId(share.getUserId())
                .articleId(share.getArticleId())
                .platform(share.getPlatform())
                .createdAt(share.getCreatedAt())
                .build();
    }
    public ArticleResponse getArticleByAuthorAndId(String authorId, String articleId) {
        Article article = articleRepository.findArticleByIdAndAuthorId(authorId, articleId)
                .orElseThrow(() -> new  ResourceNotFoundException("This Article by this user"));
        return mapToResponse(article);


    }

    @Transactional
    public ArticleResponse createArticleWithFiles(
            String userId,
            CreateArticleWithFilesRequest request
    ) {

        // Validate user exists
        try {
            UserProfileResponse userProfile = userCacheService.getUserProfile(userId);
            log.info("User verified: {}", userProfile.getUsername());
        } catch (UserNotFoundException e) {
            log.error("User not found: {}", userId);
            throw new UnauthorizedException("User not found: " + userId);
        }

        // Process content blocks
        List<Article.ContentBlock> processedBlocks = processContentBlocks(request.getBlocks());

        // Create article entity
        Article article = Article.builder()
                .authorId(userId)
                .title(request.getTitle())
                .slug(generateUniqueSlug(request.getTitle()))
                .subtitle(request.getSubtitle())
                .status(ArticleStatus.DRAFT)
                .visibility(ArticleVisiblity.PRIVATE)
                .content(Article.Content.builder()
                        .blocks(processedBlocks)
                        .estimatedReadTime(calculateReadTimeForContentBlock(processedBlocks))
                        .build()
                )
                .metadata(Article.Metadata.builder()
                        .tags(request.getTags())
                        .category(request.getCategory())
                        .description(request.getDescription())
                        .coverImageUrl(request.getCoverImageUrl())
                        .build()
                )
                .stats(Article.Stats.builder()
                        .views(0L)
                        .reads(0L)
                        .likes(0L)
                        .commentCount(0L)
                        .shareCount(0L)
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Article saved = articleRepository.save(article);
        log.info("Article created with files: {} by user: {}", saved.getId(), userId);

        return mapToResponse(saved);
    }

    /**
     * Process content blocks and validate file references
     */
    private List<Article.ContentBlock> processContentBlocks(List<FlexibleContentBlockRequest> blocks) {
        return blocks.stream()
                .map(block -> {
                    // Validate block data based on type
                    validateBlock(block);
                    // For image/video blocks, validate file exists
                    if ("image".equals(block.getType()) || "video".equals(block.getType())) {
                        validateFileReference(block.getFileId());
                    }
                    // Convert to entity
                    return Article.
                            ContentBlock
                            .builder()
                            .type(block.getType())
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * Validate individual content block
     */
    private void validateBlock(FlexibleContentBlockRequest block) {
        switch (block.getType()) {
            case "text":
                if (block.getText() == null || block.getText().isBlank()) {
                    throw new IllegalArgumentException("Text block requires 'text' field");
                }
                break;
            case "heading":
                if (block.getLevel() == null || block.getLevel() < 1 || block.getLevel() > 6) {
                    throw new IllegalArgumentException("Heading block requires valid 'level' (1-6)");
                }
                if (block.getText() == null || block.getText().isBlank()) {
                    throw new IllegalArgumentException("Heading block requires 'text' field");
                }
                break;
            case "image":
            case "video":
                if (block.getFileId() == null || block.getFileUrl() == null) {
                    throw new IllegalArgumentException(
                            block.getType() + " block requires 'fileId' and 'fileUrl' fields"
                    );
                }
                break;
            case "code":
                if (block.getCode() == null || block.getCode().isBlank()) {
                    throw new IllegalArgumentException("Code block requires 'code' field");
                }
                break;
            case "quote":
                if (block.getText() == null || block.getText().isBlank()) {
                    throw new IllegalArgumentException("Quote block requires 'text' field");
                }
                break;
            case "embed":
                if (block.getProvider() == null || block.getEmbedUrl() == null) {
                    throw new IllegalArgumentException(
                            "Embed block requires 'provider' and 'embedUrl' fields"
                    );
                }
                break;
            case "divider":
                // No specific validation needed
                break;
            default:
                throw new IllegalArgumentException("Unknown block type: " + block.getType());
        }
    }


    private Article.ContentBlock convertFlexibleBlockToEntity(
            FlexibleContentBlockRequest blockRequest
    ) {
        if (blockRequest == null) {
            throw new IllegalArgumentException("Content block cannot be null");
        }


        // Validate block
        validateFlexibleContentBlock(blockRequest);

        // For image/video blocks, validate file exists in File Service
        if ("image".equals(blockRequest.getType()) || "video".equals(blockRequest.getType())) {
            validateFileReference(blockRequest.getFileId());
        }

        // Convert DTO to JsonNode for flexible MongoDB storage
        JsonNode blockData = objectMapper.valueToTree(blockRequest);

        // Create and return entity
        return Article.ContentBlock.builder()
                .type(blockRequest.getType())
                .data(blockData)
                .build();
    }

    private Article.ContentBlock convertContentBlockRequestToEntity(
            ContentBlockRequest blockRequest
    ) {
        if (blockRequest == null) {
            throw new IllegalArgumentException("Content block cannot be null");
        }

        JsonNode blockData = objectMapper.valueToTree(blockRequest);

        return Article.ContentBlock.builder()
                .type(blockRequest.getType())
                .data(blockData)
                .build();
    }

    // ============= VALIDATION METHODS =============

    /**
     * Validate flexible content block structure
     */
    private void validateFlexibleContentBlock(FlexibleContentBlockRequest block) {
        String type = block.getType();

        switch (type) {
            case "text":
                if (block.getText() == null || block.getText().isBlank()) {
                    throw new IllegalArgumentException("Text block requires non-empty 'text' field");
                }
                if (block.getText().length() > 10000) {
                    throw new IllegalArgumentException("Text block exceeds maximum length");
                }
                break;

            case "heading":
                if (block.getLevel() == null || block.getLevel() < 1 || block.getLevel() > 6) {
                    throw new IllegalArgumentException("Heading level must be 1-6");
                }
                if (block.getText() == null || block.getText().isBlank()) {
                    throw new IllegalArgumentException("Heading block requires 'text'");
                }
                break;

            case "image":
                if (block.getFileId() == null || block.getFileId().isBlank()) {
                    throw new IllegalArgumentException("Image block requires 'fileId'");
                }
                if (block.getFileUrl() == null || block.getFileUrl().isBlank()) {
                    throw new IllegalArgumentException("Image block requires 'fileUrl'");
                }
                if (block.getAlt() == null || block.getAlt().isBlank()) {
                    throw new IllegalArgumentException("Image block requires 'alt' text for accessibility");
                }
                break;

            case "video":
                if (block.getFileId() == null || block.getFileId().isBlank()) {
                    throw new IllegalArgumentException("Video block requires 'fileId'");
                }
                if (block.getFileUrl() == null || block.getFileUrl().isBlank()) {
                    throw new IllegalArgumentException("Video block requires 'fileUrl'");
                }
                if (block.getDuration() == null || block.getDuration() < 1) {
                    throw new IllegalArgumentException("Video block requires valid 'duration'");
                }
                break;

            case "code":
                if (block.getCode() == null || block.getCode().isBlank()) {
                    throw new IllegalArgumentException("Code block requires 'code'");
                }
                if (block.getLanguage() == null) {
                    throw new IllegalArgumentException("Code block requires 'language'");
                }
                break;

            case "quote":
                if (block.getText() == null || block.getText().isBlank()) {
                    throw new IllegalArgumentException("Quote block requires 'text'");
                }
                break;

            case "embed":
                if (block.getProvider() == null || block.getProvider().isBlank()) {
                    throw new IllegalArgumentException("Embed block requires 'provider'");
                }
                if (block.getEmbedUrl() == null || block.getEmbedUrl().isBlank()) {
                    throw new IllegalArgumentException("Embed block requires 'embedUrl'");
                }
                break;

            case "divider":
                // No validation needed
                break;

            default:
                throw new IllegalArgumentException("Unknown block type: " + type);
        }
    }

    /**
     * Validate file reference exists in File Service
     */
    private void validateFileReference(String fileId) {
        if (fileId == null || fileId.isBlank()) {
            throw new IllegalArgumentException("File ID cannot be null");
        }

        try {
            // Call File Service to verify file exists
            fileServiceClient.getFileMetadata(fileId);
            log.debug("File validated: {}", fileId);
        } catch (Exception e) {
            log.error("File validation failed: {} - {}", fileId, e.getMessage());
            throw new IllegalArgumentException(
                    "File not found or inaccessible: " + fileId + ". Upload file first."
            );
        }
    }

    // ============= UTILITY METHODS =============

    /**
     * Calculate estimated read time from content blocks
     * Average: 200 words per minute
     */
    private Integer calculateReadTimeForContentBlock(List<Article.ContentBlock> blocks) {
        int totalWords = 0;

        for (Article.ContentBlock block : blocks) {
            if (block.getData() != null) {
                String text = block.getData().toString();
                int words = text.split("\\s+").length;
                totalWords += words;
            }
        }

        int minutes = Math.max(1, totalWords / 200);
        log.debug("Calculated read time: {} minutes", minutes);

        return minutes;
    }
    /**
     * Extract keywords from title and description
     */
    private List<String> extractKeywords(String title, String description) {
        if (title == null && description == null) {
            return List.of();
        }

        String combined = (title != null ? title : "") + " " + (description != null ? description : "");

        return List.of(combined.split("\\s+")).stream()
                .filter(word -> word.length() > 3)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    // ============= RESPONSE MAPPING =============

    /**
     * Map Article entity to ArticleResponse DTO
     */
    private ArticleResponse mapToResponse(Article article, UserProfileResponse author) {
        return ArticleResponse.builder()
                .id(article.getId())
                .slug(article.getSlug())
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .content(mapContentToResponse(article.getContent()))
                .metadata(mapMetadataToResponse(article.getMetadata()))
                .status(article.getStatus())
                .visibility(article.getVisibility())
                .stats(mapStatsToResponse(article.getStats()))
                .author(mapAuthorResponse(author))
                .publishedAt(article.getPublishedAt())
                .updatedAt(article.getUpdatedAt())
                .createdAt(article.getCreatedAt())
                .estimatedReadTime(article.getContent().getEstimatedReadTime())
                .build();
    }

    private ContentResponse mapContentToResponse(Article.Content content) {
        return ContentResponse.builder()
                .blocks(content.getBlocks().stream()
                        .map(block -> ContentBlockResponse.builder()
                                .type(block.getType())
                                .data(block.getData())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .estimatedReadTime(content.getEstimatedReadTime())
                .build();
    }

    private MetadataResponse mapMetadataToResponse(Article.Metadata metadata) {
        return MetadataResponse.builder()
                .tags(metadata.getTags())
                .category(metadata.getCategory())
                .description(metadata.getDescription())
                .keywords(metadata.getKeywords())
                .coverImageUrl(metadata.getCoverImageUrl())
                .build();
    }

    private StatsResponse mapStatsToResponse(Article.Stats stats) {
        return StatsResponse.builder()
                .views(stats.getViews())
                .reads(stats.getReads())
                .likes(stats.getLikes())
                .commentCount(stats.getCommentCount())
                .shareCount(stats.getShareCount())
                .lastEngagedAt(stats.getLastEngagedAt())
                .build();
    }

    private AuthorResponse mapAuthorResponse(UserProfileResponse user) {
        return AuthorResponse.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .profileImageUrl(user.getProfileImageUrl())
                .totalArticles(user.getTotalArticles() != null ? user.getTotalArticles() : 0L)
                .build();
    }
}

```

### src/main/java/com/final_project/blog_service/service/FileUploadService.java

`$lang
package com.final_project.blog_service.service;
import com.final_project.blog_service.client.FileServiceClient;
import com.final_project.blog_service.dto.response.FileUploadResponse;
import com.final_project.blog_service.dto.response.ImageUploadResponse;
import com.final_project.blog_service.dto.response.VideoUploadResponse;
import com.final_project.blog_service.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileServiceClient fileServiceClient;
    @Value("${app.file-upload.max-image-size:10485760}") // 10MB
    private long maxImageSize;
    @Value("${app.file-upload.max-video-size:536870912}") // 500MB
    private long maxVideoSize;
    private static final String[] ALLOWED_IMAGE_TYPES = {
            "image/jpeg", "image/png", "image/webp", "image/gif"
    };

    private static final String[] ALLOWED_VIDEO_TYPES = {
            "video/mp4", "video/webm", "video/ogg", "video/quicktime"
    };

    /**
     * Upload image file
     */
    @Transactional
    public ImageUploadResponse uploadImage(
            MultipartFile file,
            String alt,
            String caption,
            String userId
    ) {
        log.info("Uploading image for user: {}", userId);

        // Validate file
        validateFile(file, ALLOWED_IMAGE_TYPES, maxImageSize, "Image");

        try {
            // Upload to File Service
            FileUploadResponse response = fileServiceClient.uploadFile(
                    file,
                    "image",
                    null
            );

            log.info("Image uploaded successfully: {}", response.getFileId());

            // Return formatted response
            return ImageUploadResponse.builder()
                    .fileId(response.getFileId())
                    .fileUrl(response.getCdnUrl())
                    .thumbnailUrl(response.getThumbnailUrl())
                    .width(response.getImageWidth())
                    .height(response.getImageHeight())
                    .alt(alt)
                    .caption(caption)
                    .mimeType(response.getMimeType())
                    .fileSize(response.getFileSize())
                    .uploadedAt(response.getUploadedAt())
                    .build();
        } catch (Exception e) {
            log.error("Failed to upload image: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to upload image: " + e.getMessage(), e);
        }
    }

    /**
     * Upload video file
     */
    @Transactional
    public VideoUploadResponse uploadVideo(
            MultipartFile file,
            String title,
            String description,
            String userId
    ) {
        log.info("Uploading video for user: {}", userId);

        // Validate file
        validateFile(file, ALLOWED_VIDEO_TYPES, maxVideoSize, "Video");

        try {
            // Upload to File Service
            FileUploadResponse response = fileServiceClient.uploadFile(
                    file,
                    "video",
                    null
            );

            log.info("Video uploaded successfully: {}", response.getFileId());

            // Return formatted response
            return VideoUploadResponse.builder()
                    .fileId(response.getFileId())
                    .fileUrl(response.getCdnUrl())
                    .thumbnailUrl(response.getThumbnailUrl())
                    .duration(response.getVideoDurationSeconds())
                    .title(title)
                    .description(description)
                    .mimeType(response.getMimeType())
                    .fileSize(response.getFileSize())
                    .processingStatus(response.getProcessingStatus())
                    .uploadedAt(response.getUploadedAt())
                    .build();
        } catch (Exception e) {
            log.error("Failed to upload video: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to upload video: " + e.getMessage(), e);
        }
    }

    /**
     * Delete file
     */
    @Transactional
    public void deleteFile(String fileId, String userId) {
        log.info("Deleting file: {} for user: {}", fileId, userId);

        try {
            fileServiceClient.deleteFile(fileId);
            log.info("File deleted successfully: {}", fileId);
        } catch (Exception e) {
            log.error("Failed to delete file: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    /**
     * Validate file before upload
     */
    private void validateFile(
            MultipartFile file,
            String[] allowedTypes,
            long maxSize,
            String fileTypeName
    ) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException(fileTypeName + " file is required");
        }

        // Check file size
        if (file.getSize() > maxSize) {
            long maxMB = maxSize / 1024 / 1024;
            throw new FileUploadException(
                    fileTypeName + " file is too large. Max size: " + maxMB + "MB"
            );
        }

        // Check MIME type
        String mimeType = file.getContentType();
        if (mimeType == null || !isAllowedMimeType(mimeType, allowedTypes)) {
            throw new FileUploadException(
                    "Invalid " + fileTypeName.toLowerCase() + " format. " +
                            "Supported formats: " + String.join(", ", allowedTypes)
            );
        }

        // Check file extension
        String filename = file.getOriginalFilename();
        if (filename == null || !isValidFilename(filename)) {
            throw new FileUploadException("Invalid filename");
        }
    }

    /**
     * Check if MIME type is allowed
     */
    private boolean isAllowedMimeType(String mimeType, String[] allowedTypes) {
        for (String allowedType : allowedTypes) {
            if (mimeType.equals(allowedType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate filename
     */
    private boolean isValidFilename(String filename) {
        // Check for path traversal
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            return false;
        }

        // Check file extension
        return filename.matches("^[a-zA-Z0-9._-]+\\.(jpg|jpeg|png|gif|webp|mp4|webm|ogg|mov)$");
    }
}
```

### src/main/java/com/final_project/blog_service/service/UserCacheService.java

`$lang
package com.final_project.blog_service.service;
import com.final_project.blog_service.client.UserServiceClient;
import com.final_project.blog_service.dto.response.UserProfileResponse;
import com.final_project.blog_service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheService {
    private final UserServiceClient userServiceClient;
    private final RedisTemplate<String, UserProfileResponse> redisTemplate;

    private static final String USER_CACHE_KEY = "user:";
    private static final String USER_EMAIL_CACHE_KEY = "user:email:";
    private static final int CACHE_TTL_MINUTES = 30;

    /**
     * Get user profile with caching
     * Tries cache first, then calls User Service, falls back to empty profile
     */
    public UserProfileResponse getUserProfile(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        String cacheKey = USER_CACHE_KEY + userId;

        // Try cache first
        try {
            UserProfileResponse cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) {
                log.debug("User cache hit: {}", userId);
                return cached;
            }
        } catch (Exception e) {
            log.warn("Cache read error for user {}: {}", userId, e.getMessage());
        }

        // Cache miss - call User Service
        try {
            log.debug("Fetching user profile from User Service: {}", userId);
            UserProfileResponse profile = userServiceClient.getUserProfile(userId);

            // Cache the result
            cacheUserProfile(userId, profile);

            return profile;
        } catch (UserNotFoundException e) {
            log.error("User not found in User Service: {}", userId);
            throw e;
        } catch (Exception e) {
            log.error("User Service call failed for user {}: {}", userId, e.getMessage());

            // Try fallback: return user ID as profile
            return createFallbackProfile(userId);
        }
    }

    /**
     * Get user by email
     */
    public UserProfileResponse getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        String cacheKey = USER_EMAIL_CACHE_KEY + email.toLowerCase();

        try {
            UserProfileResponse cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) {
                log.debug("User cache hit by email: {}", email);
                return cached;
            }
        } catch (Exception e) {
            log.warn("Cache read error for email {}: {}", email, e.getMessage());
        }

        try {
            UserProfileResponse profile = userServiceClient.getUserByEmail(email);
            cacheUserProfile(profile.getId(), profile);
            return profile;
        } catch (Exception e) {
            log.error("User Service call failed for email {}: {}", email, e.getMessage());
            throw new UserNotFoundException("User not found: " + email);
        }
    }

    /**
     * Get multiple user profiles efficiently with caching
     */
    public Map<String, UserProfileResponse> getUserProfiles(List<String> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, UserProfileResponse> result = new HashMap<>();
        List<String> uncachedUserIds = new ArrayList<>();

        // Check cache for each user
        for (String userId : userIds) {
            String cacheKey = USER_CACHE_KEY + userId;
            try {
                UserProfileResponse cached = redisTemplate.opsForValue().get(cacheKey);
                if (cached != null) {
                    result.put(userId, cached);
                } else {
                    uncachedUserIds.add(userId);
                }
            } catch (Exception e) {
                uncachedUserIds.add(userId);
            }
        }

        // Batch fetch uncached users
        if (!uncachedUserIds.isEmpty()) {
            try {
                log.debug("Batch fetching {} users from User Service", uncachedUserIds.size());
                List<UserProfileResponse> profiles = userServiceClient.getMultipleUsers(uncachedUserIds);

                for (UserProfileResponse profile : profiles) {
                    result.put(profile.getId(), profile);
                    cacheUserProfile(profile.getId(), profile);
                }

                // Add fallback profiles for missing users
                Set<String> fetchedIds = profiles.stream()
                        .map(UserProfileResponse::getId)
                        .collect(Collectors.toSet());

                uncachedUserIds.stream()
                        .filter(id -> !fetchedIds.contains(id))
                        .forEach(id -> result.put(id, createFallbackProfile(id)));

            } catch (Exception e) {
                log.error("Batch fetch failed: {}", e.getMessage());

                // Fallback: return all as fallback profiles
                uncachedUserIds.forEach(id -> result.put(id, createFallbackProfile(id)));
            }
        }

        return result;
    }

    /**
     * Invalidate user cache (call when user updates profile)
     */
    public void invalidateUserCache(String userId) {
        if (userId == null) return;

        String cacheKey = USER_CACHE_KEY + userId;
        try {
            redisTemplate.delete(cacheKey);
            log.debug("User cache invalidated: {}", userId);
        } catch (Exception e) {
            log.warn("Failed to invalidate cache for user {}: {}", userId, e.getMessage());
        }
    }

    /**
     * Invalidate user cache by email
     */
    public void invalidateUserCacheByEmail(String email) {
        if (email == null) return;

        String cacheKey = USER_EMAIL_CACHE_KEY + email.toLowerCase();
        try {
            redisTemplate.delete(cacheKey);
            log.debug("User cache invalidated by email: {}", email);
        } catch (Exception e) {
            log.warn("Failed to invalidate cache for email {}: {}", email, e.getMessage());
        }
    }

    // ============= PRIVATE METHODS =============

    private void cacheUserProfile(String userId, UserProfileResponse profile) {
        String cacheKey = USER_CACHE_KEY + userId;
        try {
            redisTemplate.opsForValue().set(
                    cacheKey,
                    profile,
                    CACHE_TTL_MINUTES,
                    TimeUnit.MINUTES
            );
            log.debug("User profile cached: {}", userId);
        } catch (Exception e) {
            log.warn("Failed to cache user profile: {}: {}", userId, e.getMessage());
        }
    }

    /**
     * Create fallback profile when User Service is unavailable
     */
    private UserProfileResponse createFallbackProfile(String userId) {
        log.warn("Creating fallback profile for user: {}", userId);

        return UserProfileResponse.builder()
                .id(userId)
                .displayName("[User Service Unavailable]")
                .username("user_" + userId.substring(0, Math.min(8, userId.length())))
                .email(null)
                .profileImageUrl(null)
                .bio(null)
                .totalArticles(0L)
                .createdAt(null)
                .build();
    }
}

```

### src/main/java/com/final_project/blog_service/utile/ContentBlockValidator.java

`$lang
package com.final_project.blog_service.utile;

import com.fasterxml.jackson.databind.JsonNode;
import com.final_project.blog_service.dto.request.ContentBlockRequest;

import java.util.List;

public class ContentBlockValidator {

    private static final List<String> VALID_BLOCK_TYPES = List.of(
            "text", "heading", "image", "video", "code", "quote", "embed", "divider"
    );

    public static boolean isValidBlockType(String type) {
        return VALID_BLOCK_TYPES.contains(type);
    }

    public static void validateBlock(ContentBlockRequest block) {
        if (!isValidBlockType(block.getType())) {
            throw new IllegalArgumentException("Invalid block type: " + block.getType());
        }

        JsonNode data = block.getData();

        switch (block.getType()) {
            case "text":
                if (!data.has("text") || data.get("text").asText().isEmpty()) {
                    throw new IllegalArgumentException("Text block must have non-empty 'text' field");
                }
                break;

            case "heading":
                if (!data.has("level") || !data.has("text")) {
                    throw new IllegalArgumentException("Heading block must have 'level' and 'text' fields");
                }
                int level = data.get("level").asInt();
                if (level < 1 || level > 6) {
                    throw new IllegalArgumentException("Heading level must be 1-6");
                }
                break;

            case "image":
                if (!data.has("fileUrl")) {
                    throw new IllegalArgumentException("Image block must have 'fileUrl'");
                }
                break;

            case "code":
                if (!data.has("code")) {
                    throw new IllegalArgumentException("Code block must have 'code' field");
                }
                break;

            case "quote":
                if (!data.has("text")) {
                    throw new IllegalArgumentException("Quote block must have 'text' field");
                }
                break;

            case "embed":
                if (!data.has("provider") || !data.has("embedUrl")) {
                    throw new IllegalArgumentException("Embed block must have 'provider' and 'embedUrl'");
                }
                break;
        }
    }
}
```

### src/main/java/com/final_project/blog_service/utile/ReadTimeCalculator.java

`$lang
package com.final_project.blog_service.utile;


public class ReadTimeCalculator {

    private static final int WORDS_PER_MINUTE = 200;

    public static int calculateReadTime(String content) {
        if (content == null || content.isEmpty()) {
            return 1;
        }

        String[] words = content.split("\\s+");
        int wordCount = words.length;

        int readTime = (wordCount + WORDS_PER_MINUTE - 1) / WORDS_PER_MINUTE;

        return Math.max(1, readTime);
    }

    public static String formatReadTime(int minutes) {
        if (minutes <= 1) {
            return "1 min read";
        }
        return minutes + " min read";
    }
}
```

### src/main/java/com/final_project/blog_service/utile/SlugUtil.java

`$lang
package com.final_project.blog_service.utile;


import org.apache.commons.lang3.StringUtils;

public class SlugUtil {

    public static String generateSlug(String title) {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        return title
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    public static String slugify(String input) {
        return generateSlug(input);
    }
}

```

### src/main/resources/application.yaml

`$lang
spring:
  application:
    name: blog-service
  config:
    import: optional:configserver:http://localhost:8888
```

### src/test/java/com/final_project/blog_service/BlogServiceApplicationTests.java

`$lang
package com.final_project.blog_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
```


