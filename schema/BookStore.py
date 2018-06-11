#! /usr/bin/python
from faker import Faker
from random import randint
import csv

def randN(n):
    range_start = 10**(n-1)
    range_end = (10**n)-1
    return randint(range_start, range_end)

SIZE = 1000000
fake = Faker('it_IT')

#Genre
genre = [0 for i in range(5)]
genre [0] = 'Art'
genre [1] = 'Geography'
genre [2] = 'History'
genre [3] = 'Religion'
genre [4] = 'Science'

with open('genre.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(5):
        wr.writerow([genre[i]])

#User
user_name = [str(fake.sha1()) for _ in range(SIZE)]
user_firstname = [str(fake.first_name()) for _ in range(SIZE)]
user_lastname = [str(fake.last_name()) for _ in range(SIZE)]
user_email = [fake.email() for _ in range(SIZE)]
user_phone = [fake.phone_number() for _ in range(SIZE)]
user_address = [fake.street_address() for _ in range(SIZE)]

with open('user.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(SIZE):
        wr.writerow([user_name[i]] + [user_firstname[i]] + [user_lastname[i]] + [user_email[i]] + [user_phone[i]] + [user_address[i]])

#Publisher
publisher_id = [i for i in range(SIZE)]
publisher_name = [str(fake.name()) for _ in range(SIZE)]
publisher_address = [fake.street_address() for _ in range(SIZE)]
publisher_phone = [fake.phone_number() for _ in range(SIZE)]

with open('publisher.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(SIZE):
        wr.writerow([publisher_id[i]] + [publisher_name[i]] + [publisher_address[i]] + [publisher_phone[i]])

#Book
book_isbn = [randN(13) for _ in range(SIZE)]
book_title = [fake.sentence(3) for _ in range(SIZE)]
book_publicationyear = [fake.year() for _ in range(SIZE)]
book_price = [i * 4.4 for i in range(SIZE)]
book_stock = [i * 10 for i in range(SIZE)]
book_minQuantity = [i for i in range(SIZE)]

with open('book.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(SIZE):
        wr.writerow([book_isbn[i]] + [book_title[i]] + [genre[randint(0, 4)]] + [publisher_id[randint(0, SIZE -1)]] + [book_publicationyear[i]] +
        [book_price[i]] + [book_stock[i]] + [book_minQuantity[i]])
#Author
author_name = [str(fake.name()) for _ in range(SIZE)]

with open('author.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(SIZE):
        wr.writerow([author_name[i]] + [book_isbn[randint(0, SIZE -1)]])
        wr.writerow([author_name[i]] + [book_isbn[i]])

#Order
order_id = [i for i in range(SIZE)]
order_quantity = [i for i in range(SIZE)]

with open('order.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(SIZE):
        wr.writerow([order_id[i]] + [book_isbn[randint(0, SIZE -1)]] + [order_quantity[i]] )

#Sales
sales_time = [str(fake.iso8601()).replace("T"," ") for _ in range(SIZE)]
sales_quantity = [i for i in range(SIZE)]
with open('sales.csv', 'w') as f:
    wr = csv.writer(f, lineterminator='\n')
    for i in range(SIZE):
        wr.writerow([sales_time[i]] + [user_name[randint(0, SIZE -1)]] + [book_isbn[randint(0, SIZE -1)]] + [sales_quantity[i]] )