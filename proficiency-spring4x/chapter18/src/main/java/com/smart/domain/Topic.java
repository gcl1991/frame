package com.smart.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_topic")
@Getter
@Setter
public class Topic extends BaseDomain {
	/**
	 * 精华主题帖子
	 */
	public static final int DIGEST_TOPIC = 1;
	/**
	 * 普通的主题帖子
	 */
	public static final int NOT_DIGEST_TOPIC = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_id")
	private int topicId;

	@Column(name = "topic_title")
	private String topicTitle;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "board_id")
	private int boardId;

	@Transient
	private MainPost mainPost = new MainPost();

	@Column(name = "last_post")
	private Date lastPost = new Date();

	@Column(name = "create_time")
	private Date createTime = new Date();

	@Column(name = "topic_views")
	private int views;

	@Column(name = "topic_replies")
	private int replies;

	private int digest = NOT_DIGEST_TOPIC;
}
