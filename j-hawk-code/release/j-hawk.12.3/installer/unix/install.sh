echo "Copying hawk to /usr/local/bin"
sudo cp  ./hawk /usr/local/bin
rm -rf ~/.hawk
mkdir ~/.hawk
echo "Copying hawk files..."
cp ../../j-hawk.jar ~/.hawk
echo "Creating hawk manual page"
sudo cp ./hawk.1.gz /usr/share/man/man1

