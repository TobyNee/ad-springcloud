package com.imooc.ad.entity;

import com.imooc.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "username", nullable = false)
	private String username;

	@Basic
	@Column(name = "token", nullable = false)
	private String token;

	@Basic
	@Column(name = "user_status", nullable = false)
	private Integer userStatus;

	@Basic
	@Column(name = "create_time", nullable = true)
	private LocalDateTime createTime;

	@Basic
	@Column(name = "update_time", nullable = true)
	private LocalDateTime updateTime;

	public AdUser(String username, String token) {
		this.username = username;
		this.token = token;
		this.userStatus = CommonStatus.VILID.getStatus();
	}

}
