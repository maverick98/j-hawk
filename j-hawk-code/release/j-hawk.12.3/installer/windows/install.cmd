echo "installing hawk..."

echo "removing old hawk"

rmdir /S /Q "%PROGRAMFILES%/hawk"

echo "creating hawk"

mkdir "%PROGRAMFILES%/hawk"

echo "copying j-hawk.jar"

copy ..\j-hawk.jar "%PROGRAMFILES%/hawk"

echo "copying hawk.exe"

copy hawk.exe "%PROGRAMFILES%/hawk"

echo add "%PROGRAMFILES%\hawk" to PATH environment variable
