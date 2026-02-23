# Exercise 2
1. **List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**

Ada banyak code quality issues yang muncul, salah satunya adalah unecessary modifier public, dimana beberapa method pada interface menggunakan modifier public secara implisit. Saya mengatasi issue ini dengan mengurangi public modifiers yang redundan agar code menjadi lebih bersih. Selain itu, saya menemukan code issue yang menarik terkait constructor. Apabila sebuah class tidak memiliki constructor, PMD akan men-flag code itu sebagai issue **"each class should declare at least one constructor"**. Namun, apabila kita menghapus constructornya, maka PMD juga akan flag code itu sebagai issue **"avoid unecessary constructors"**. Maka, disini solusi yang saya buat adalah saya tetap membuat constructornya, lalu menambahkan super(). Dengan pendekatan ini, structure code tetap aman, dan PMD tidak lagi flag code-nya sebagai issue.  


2. **Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!**
  
Menurut saya, CI/CD workflows nya sudah cukup memenuhi definisi dari Continious Integration and Continuous Deployment. Pada sisi CI, setiap kali terjadi push maupun pull, workflow langsung melakukan build dan menjalankan unit test, serta melaporkan test coverage dari code. Selain itu, PMD juga mengecek dari sisi quality code, apakah ada issue yang ditemukan. Bagi saya ini sudah cukup holistik dan sesuai  standart Continious Integration. Pada sisi CD, Heroku langsung melakukan deployment secara otomatis setiap code di push ke main branch. Sehingga, hal ini mempermudah dan mempercepat proses deployment. Dengan demikian, menurut saya CI/CD workflowsnya sudah sesuai dengan definisi dan dapat mempermudah developer untuk menjaga kualiitas code, serta mempercepat proses development & deployment.



# Exercise 1
Berdasarkan penambahan 2 fitur baru yaitu fitur untuk mengedit produk dan menghapus produk, saya menerapkan beberapa prinsip yang telah diajarkan pada materi pekan ini. 
1. **Meaningful & Descriptive Names**  
Saya membuat nama fungsi yang singkat namun deskriptif, misal untuk mendapatkan produk dengan id, saya menamai fungsinya findById. Fungsi lain seperti edit dan delete juga sudah cukup deskriptif dalam menjelaskan apa yang dilakukan pada kode tersebut.


2. **Do One Thing & Small Functions**  
    Berdasarkan materi pekan ini, dikatakan bahwa fungsi harus melakukan 1 hal saja agar kode lebih mudah dibaca dan diharapkan reusable kedepannya. Oleh karena itu, saya juga menerapkan hal tersebut, dimana setiap fungsi hanya menjalankan 1 tujuan, yang juga tetap menjaga agar fungsi tetap kecil.
    

3.  **Secure Coding**  
    Dengan penulisan clean code, serta penggunaan HTTP methods GET dan POST, saya juga mencoba untuk membuat kode menjadi lebih aman dan secured.


walau begitu, saya menyadari ada beberapa kekurangan dari kode yang sudah ditulis, misal belum adanya validasi ketika user memberikan input yang salah pada form produk, dimana hal ini bisa diimprove dengan menambahkan validator @NotNull, @Min(0), etc. Selain itu, pada method findById pun bisa saja ID produk tidak ditemukan, yang mana ini bisa diimprove dengan menambahkan try exception. Sehingga secara keseluruhan, kode yang ditulis belum benar-benar secured dan clean sehingga masih bisa diperbaiki lagi.

# Exercise 2
1. Setelah menulis unit test, saya merasa saya sudah membuat kode menjadi lebih aman dari error dan bug yang mungkin terjadi. Menurut saya, unit test sendiri harus dibuat sebanyak mungkin untuk tiap class sehingga dapat mengcover edge-case, hal ini bisa diukur melalui code coverage ketika sudah mencapai 100%. Walau begitu, belum tentu code coverage 100% artinya code aman dari bug atau error, karena code coverage hanya menyatakan bahwa code telah dieksekusi dari semua test yang dibuat oleh developer, tidak menjamin logic, bug, serta edge case lain yang mungkin belum di test.


2. Menurut saya, dengan membuat test baru dengan kode yang mirip-mirip kurang mencerminkan prinsip clean code, karena kode menjadi redundan dan terlalu banyak. Sehingga, hal ini dapat mengurangi kemampuan code agar reusable. Untuk mengimprove hal tersebut, developer dapat memindahkan setup code ke helper class agar dapat digunakan berulang kali. Dengan begitu, kode jadi tidak terlalu redundan, lebih mudah dibaca dan dapat digunakan kembali.