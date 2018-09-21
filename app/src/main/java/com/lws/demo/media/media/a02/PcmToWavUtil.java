package com.lws.demo.media.media.a02;

import android.media.AudioFormat;
import android.media.AudioRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PcmToWavUtil {
    /**
     * 缓存的音频大小
     */
    private int mBufferSize;
    /**
     * 采样率
     */
    private int mSampleRate;
    /**
     * 声道数
     */
    private int mChannel;

    /**
     * @param sampleRate sample rate 采样率
     * @param channel    channel 声道
     * @param encoding   Audio data format 音频格式
     */
    public PcmToWavUtil(int sampleRate, int channel, int encoding) {
        mSampleRate = sampleRate;
        mChannel = channel;
        mBufferSize = AudioRecord.getMinBufferSize(mSampleRate, mChannel, encoding);
    }

    /**
     * pcm文件转wav文件
     *
     * @param inFileName  源文件路径
     * @param outFileName 目标文件路径
     */
    public void pcmToWav(String inFileName, String outFileName) {
        FileInputStream in;
        FileOutputStream out;
        long totalAudioLen;
        long totalDataLen;
        long longSampleRate = mSampleRate;
        int channels = mChannel == AudioFormat.CHANNEL_IN_MONO ? 1 : 2;
        long byteRate = 16 * mSampleRate * channels / 8;
        byte[] data = new byte[mBufferSize];
        try {
            in = new FileInputStream(inFileName);
            out = new FileOutputStream(outFileName);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

            writeWaveFileHeader(out, totalAudioLen, totalDataLen, longSampleRate, channels, byteRate);
            while (in.read(data) != -1) {
                out.write(data);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入wav文件头,https://baike.baidu.com/item/wav/218914
     */
    private void writeWaveFileHeader(
            FileOutputStream out, long totalAudioLen, long totalDataLen,
            long longSampleRate, int channels, long byteRate) throws IOException {
        byte[] header = new byte[44];
        // 资源交换文件标志（RIFF）
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        // 从下个地址开始到文件尾的总字节数
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        // WAV文件标志（WAVE）
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        // 波形格式标志（fmt ），最后一位空格。
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        // 过滤字节（一般为00000010H），若为00000012H则说明数据头携带附加信息
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        // 格式种类（值为1时，表示数据为线性PCM编码）
        header[20] = 1;
        header[21] = 0;
        // 通道数，单声道为1，双声道为2
        header[22] = (byte) channels;
        header[23] = 0;
        // 采样频率
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        // 波形数据传输速率（每秒平均字节数）
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // block align
        header[32] = (byte) (2 * 16 / 8);
        header[33] = 0;
        // bits per sample
        header[34] = 16;
        header[35] = 0;
        //data
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }
}
