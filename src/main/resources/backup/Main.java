package com.todayz;

public class Main {
	public static void main(String[] args) {
		for (int index = 0; index <= 100; index++) {
			System.out.println("insert into image (image_id, image_name, item_id, type) values (" + index
					+ ", 'jmlimpng" + index + "', null, 'png');");
			System.out.println(
					"insert into member (member_id, auth_name, birthday, description, join_date, name, password, phone_number, image_id) values ("
							+ index + ", 'jmlimid" + index + "', '1986-08-30', '안녕하세요', '1986-08-30', '임정묵" + index
							+ "', 'jmlimpass', '010-8791-1883', " + index + ");");
		}
	}
}
