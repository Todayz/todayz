package com.todayz.domain.chat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.club.Club;
import com.todayz.domain.member.Member;

import lombok.Getter;
import lombok.Setter;

//메세지 저장을 디비로 하는것이 맞는지는 고민 필요.
@Entity
@Table(name = "TODAYZ_CHAT")
@Getter
@Setter
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CHAT_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLUB_ID")
	private Club parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member writer;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDate;

	private String content;
}