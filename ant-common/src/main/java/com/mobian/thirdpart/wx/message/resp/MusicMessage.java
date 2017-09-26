package com.mobian.thirdpart.wx.message.resp;

/**
 * 音乐消息
 * 
 * @author liufeng
 * @date 2013-05-19
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private com.mobian.thirdpart.wx.message.resp.Music Music;

	public com.mobian.thirdpart.wx.message.resp.Music getMusic() {
		return Music;
	}

	public void setMusic(com.mobian.thirdpart.wx.message.resp.Music music) {
		Music = music;
	}
}