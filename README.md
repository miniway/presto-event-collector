# Presto Collector
## Java Presto Collector

    // edit collector/EventResource.java if you want
    mvn package
    tar -xvzf presto-event-collector/target/presto-event-collector-{version}.tar.gz
    cp -a etc presto-event-collector-{version}/etc
    cd presto-event-collector-{version}
    bin/launcher start|run

## Python Presto Collector

    sudo pip install zope.interface
    sudo pip install twisted
    git clone https://github.com/miniway/twest.git
    cd twest && sudo python setup.py install

    cd src/main/python
    // edit collector/Event.py if you want
    twistd collector -p 8088 -c config.ini
    // check twistd log
    kill `cat twistd.pid`
