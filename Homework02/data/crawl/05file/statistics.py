import csv
import pandas as pd
import numpy as np


print "Name: Biru Lyu"
print "USC ID: 1810570267"
print "News site crawled: http://www.nydailynews.com/"

print " "
print "Fetch Statistics"
print "================"
#with open('fetch_New_York_Daily_News.csv') as fetch_data:
fetch_data = pd.read_csv('fetch_New_York_Daily_News.csv')
http_status = fetch_data.groupby('httpStatusCode').count()
#print http_status
print "# fetches attempted:", fetch_data.shape[0]
print "# fetches succeeded:", http_status.iat[0,0]
sum = 0;
for i in range(1,http_status.shape[0]) :
    sum += http_status.iat[i,0]
print "# fetches aborted or failed:", sum
    # readCSV = csv.DictReader(fetch_data, delimiter=',')
    # ps = pd.Series(row['httpStatusCode'] for row in readCSV)
    # counts = ps.value_counts()
    # print(counts)

print " "
print "Outgoing URLs:"
print "================"

url_data = pd.read_csv('urls_New_York_Daily_News.csv')
print "Total URLs extracted: " ,url_data.shape[0]
url_data = url_data.drop_duplicates()
print "# unique URLs extracted: " , url_data.shape[0]
gb = url_data.groupby('isOk')
print "# unique URLs within News Site: " , gb.get_group('OK').shape[0]
print "# unique URLs outside News Site: " , gb.get_group('N_OK').shape[0]


print " "
print "Status Codes:"
print "============="
print "200 OK:", http_status.iat[0, 0]
print "301 Moved Permanently:",http_status.iat[1, 0]
print "302 Found:",http_status.iat[2, 0]
#print "401 Unauthorized:",http_status.iat[1, 0]
#print "403 Forbidden:",http_status.iat[0, 0]
print "404 Not Found:",http_status.iat[3, 0]
print "410 Gone",http_status.iat[4, 0]

print " "
print "File Sizes:"
print "================"
ranges = [0, 1 * 1024,10 * 1024, 100 * 1024, 1024 * 1024, float("inf")]
visit_data = pd.read_csv('visit_New_York_Daily_News.csv')
cnt = visit_data.groupby(pd.cut(visit_data['size'], ranges)).count()
#print cnt
print "< 1KB:", cnt.iat[0,0]
print "1KB ~ <10KB:", cnt.iat[1,0]
print "10KB ~ <100KB:", cnt.iat[2,0]
print "100KB ~ <1MB:", cnt.iat[3,0]
print ">= 1MB:", cnt.iat[4,0]


cnt2 = visit_data.groupby(visit_data['content-type']).count()
#print cnt2
print " "
print "Content Types:"
print "================"
print "text/html:", cnt2.iat[0,0]
print "image/gif:", 0
print "image/jpeg:", 0
print "image/png:", 0
print "application/pdf:", 0
# with open('crawlurls_New_York_Daily_News.csv') as url_data:
#     readCSV = csv.DictReader(url_data, delimiter = ',')
#     # for row in readCSV:
#     #     if row[1] == 'isOk':
#     #         print(row)
#     print(readCSV.)
    # ps1 = pd.Series(row['url'] for row in readCSV)
    #
    # ps2 = pd.Series(row['isOk'] for row in readCSV)
    # counts1 = ps1.value_counts()
    # print(counts1)

#with open('crawlvisit_New_York_Daily_News.csv')
