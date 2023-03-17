## Ski Swap
Ski Swap is a website for Ski Club members to exchange used skiing and snowboarding gears online. 
It provides a platform for ski club members to sell and/or buy used gears online. 

### Description
- This website application created one admin and one user account by default:
  - Admin account: Email: admin@admin.com, Password: test.
  - User account: Email: test@test.com, Password: test.
- Admin can authorize user registration, read all items posted on the website, delete user or item.
- Users can read the items posted by themselves only, post another item for sale, update and/or delete item. 

### Dependencies
- Java: 11
- Spring Boot: 2.7.8
- Spring Security: 5
- MySQL
- Thymeleaf
- Modelmapper: 2.3.8
- Bootstrap: 4.3.1
- Jquery: 3.4.1
- Font-awesome: 6.2.0

### Installation
Clone the repository to your local directory using
```
git clone https://github.com/sisi-siyuchen/ski_swap.git
```

To run this project on your local machine with Maven, using
```
mvn spring-boot:run
```

Access the website in your browser using
```
http://localhost:8080/
```

### Improvement Goals
- Enable password update feature.
- Integrate live chat into website. 

### Acknowledgement
I would like to thank Igor, Erica and my classmates from Per Scholas for their instructions and guidance along the development of this full stack project.