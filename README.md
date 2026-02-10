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