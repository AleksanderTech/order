#!/bin/sh

sudo -u postgres psql -f reset.sql
sudo -u postgres psql -f init.sql
sudo -u postgres psql -U order -d order -f schema.sql