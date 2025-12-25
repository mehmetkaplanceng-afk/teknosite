INSERT INTO kategori (id, ad) VALUES (1, 'Laptop');
INSERT INTO kategori (id, ad) VALUES (2, 'Telefon');
INSERT INTO kategori (id, ad) VALUES (3, 'Aksesuar');

INSERT INTO urun (baslik, fiyat, stok, kategori_id, gorsel_url, aciklama)
VALUES ('ThinkPad T14', 42000, 8, 1, 'https://picsum.photos/seed/laptop/600/300', 'AMD Ryzen, 16GB RAM, 512GB SSD');

INSERT INTO urun (baslik, fiyat, stok, kategori_id, gorsel_url, aciklama)
VALUES ('iPhone 15', 65000, 5, 2, 'https://picsum.photos/seed/phone/600/300', '128GB, güçlü işlemci, 5G');
