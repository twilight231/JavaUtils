package com.zstax.util.常用类.mutimedia.sound;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ʹ��javax��sound��ʵ����Ƶ�Ĳ�����
 * �ó����ܹ�������Ƶ�Ĳ�����ֹͣ����ʾ�Ϳ�����Ƶ���ŵĽ��ȣ�����������С������.
 * ֧��.wav/.au/.aiff/.mid�����ļ��Ĳ��š�
 * �ڴ���MIDI�ļ������������ļ�ʱ����������Ϊ����������Ƶ�ļ���ʱ����΢����㣬
 * ��MIDI�ļ���MIDI�ġ�ticks������
 */
public class SoundPlayer extends JComponent {
	
	// ���ֲ��ŵ��Ƿ�ΪMIDI�ļ�
    boolean isMIDI; 
    // MIDI�ļ�������
    Sequence sequence; 
    // ����MIDI���ݵ�Sequencer��
    Sequencer sequencer; 
    
    // ������Ƶ�ļ���������������
    Clip clip;
    
    // ��ʾ��Ƶ�Ƿ��ڲ��� 
    boolean playing = false;

    // ��ʾ��Ƶ�ĳ��ȣ�һ����Ƶ�ļ��ú�����㡣
    // MIDI�ļ��á�ticks��������
    int audioLength;   
    // ��¼��ǰ���ŵ�λ��
    int audioPosition = 0; 

    // ʹ�õ���Swing���
    JButton play; // ������ͣ��ť
    JSlider progress; // ������
    JLabel time;	// ����ʱ��
    Timer timer;

    // ����һ��SoundPlayer�������JFrame����ʾ
    public static void main(String[] args) 
        throws IOException,
               UnsupportedAudioFileException,
               LineUnavailableException,
               MidiUnavailableException,
               InvalidMidiDataException
    {
        SoundPlayer player;
        // �����ŵ��ļ�
        String fileName = "C:/temp/faruxue.mid";
//        String fileName = "C:/temp/test.wav";
        File file = new File(fileName); 
        // �ж��Ƿ�ΪMIDI�ļ�
        boolean ismidi;
        try {
        	// ͨ��MidiSystem���getMidiFileFormat���ļ��ж�ȡMID���ݡ�
        	// �����ȡ�ɹ��������ΪMIDI�ļ�
            MidiSystem.getMidiFileFormat(file);
            ismidi = true;
        } catch(InvalidMidiDataException e) {
        	// ��ȡʧ�ܣ���ʾ����һ��MIDI�ļ�
            ismidi = false;
        }

        // ����һ��SoundPlayer����
        player = new SoundPlayer(file, ismidi);


        // ����JFrame
        JFrame f = new JFrame("��Ƶ������");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(player, "Center");
        f.pack();
		try {
			//���ý������ۣ�Ϊϵͳ���
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
        f.setVisible(true);
    }

    // ���췽��
	public SoundPlayer(File f, boolean isMidi) throws IOException,
			UnsupportedAudioFileException, LineUnavailableException,
			MidiUnavailableException, InvalidMidiDataException {
		if (isMidi) {
			// �����MIDI�ļ�����Ҫ��sequencer����
			isMIDI = true;
			// ���Ȼ�ȡsequencer������
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// ����һ���ϳ���
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();

			// ��sequencer��ϳ�����������
			Transmitter transmitter = sequencer.getTransmitter();
			Receiver receiver = synth.getReceiver();
			transmitter.setReceiver(receiver);

			// ��MIDI�ļ���ȡsequence
			sequence = MidiSystem.getSequence(f);
			// ��ȡMIDI�ļ���ticks����	
			audioLength = (int) sequence.getTickLength();
			// ��sequence����������sequencer��
			sequencer.setSequence(sequence);
		} else { 
			// �����һ����ͨ��Ƶ�ļ�
			isMIDI = false;
			// ��ȡ��Ƶ������
			AudioInputStream ain = AudioSystem.getAudioInputStream(f);
			try {
				// ������Ƶ�ĸ�ʽ���ļ�
				DataLine.Info info = new DataLine.Info(Clip.class, ain
						.getFormat());
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(ain);
			} finally { 
				ain.close();
			}
			// ��ȡ��Ƶ�����ĳ��ȣ���΢��ת���ɺ���
			audioLength = (int) (clip.getMicrosecondLength() / 1000);
		}

		// ��ʼ��GUI
		play = new JButton("play");
		// ����һ��������������Ϊ����������Сֵ�����ֵ����ʼֵ
		progress = new JSlider(0, audioLength, 0); 
		time = new JLabel("0");// ��ʾ��ǰ���ŵ�ʱ��

		// Ϊ��ť���ʱ�䴦���������Ʋ��ź���ͣ
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playing){
					suspend();
				}else {
					play();
				}
			}
		});

		// Ϊ�������ı��¼���������
		progress.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// ����ı��˽�������ֵ����ʱ���ǩ�޸�
				int value = progress.getValue();
				if (isMIDI){
					time.setText(value + "");
				} else {
					time.setText(value / 1000 + "." + (value % 1000) / 100);
				}
				// �������λ�õ�Ŀ��ֵ�͵�ǰֵ��ͬ��������Ŀ��ֵλ��
				if (value != audioPosition) {
					skip(value);
				}
			}
		});

		// ���ƽ�������ֵ�ı仯��ÿһ���������10��
		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});

		// ��������3���������һ������У������ҵ���ʾ
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(play);
		panel1.add(progress);
		panel1.add(time);

		setLayout(new BorderLayout());
		this.add(panel1, BorderLayout.NORTH);

		// ���ӿ������
		if (isMIDI){
			addMidiControls();
		} else {
			addSampledControls();
		}
	}
    /** 
     * ������Ƶ
     */
    public void play() {
		if (isMIDI){
			sequencer.start();
		} else {
			clip.start();
		}
		timer.start();
		play.setText("suspend");
		playing = true;
	}
    /** 
     * ��ͣ��Ƶ
     */
    public void suspend() {
		timer.stop();
		// ֹͣ��Ƶ
		if (isMIDI){
			sequencer.stop();
		} else {
			clip.stop();
		}
		play.setText("play");
		playing = false;
	}
    /** 
     * ֹͣ���ţ��������Ž��ȵ���Ϊ0 
     */
    public void reset() {
		suspend();
		if (isMIDI){
			sequencer.setTickPosition(0);
		} else {
			clip.setMicrosecondPosition(0);
		}
		audioPosition = 0;
		progress.setValue(0);
	}

    /** 
     * ��ת��ָ��λ�ã����϶�������ʱ���ø÷���
     */
    public void skip(int position) {
		if (position < 0 || position > audioLength){
			return;
		}
		audioPosition = position;
		if (isMIDI){
			sequencer.setTickPosition(position);
		} else {
			clip.setMicrosecondPosition(position * 1000);
		}
		// ���½�������ֵ
		progress.setValue(position); 
	}

    /** 
     * ������Ƶ����
     */
	public int getLength() {
		return audioLength;
	}

    /**
     * Timerÿ1�����10�θ÷�����������������ֵ��
     * ����Ƶ����ʱ������������ֵ��0
     */
	void tick() {
    	// ��ȡ��ǰ��Ƶ���ŵ�λ�ã�����������ָ���λ��
        if (isMIDI && sequencer.isRunning()) {
            audioPosition = (int)sequencer.getTickPosition();
            progress.setValue(audioPosition);
        }  else if (!isMIDI && clip.isActive()) {
            audioPosition = (int)(clip.getMicrosecondPosition()/1000);
            progress.setValue(audioPosition);
        }  else {
        	reset();
        }
    }
    /**
     * һ����Ƶ�ļ��Ŀ�����
     */
    void addSampledControls() {
        try {
        	// FloatControl�ṩһϵ�еĸ���ֵ��
        	// FloatControl.Type.MASTER_GAIN��ʾ��ȡ������Ƶ������С��FloatControl����λ��dB
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			if (gainControl != null){
				// ����һ������������С�Ľ�����
				this.add(createSlider(gainControl), BorderLayout.CENTER);
			}
		} catch(IllegalArgumentException e) {
        }
        try {
        	// FloatControl.Type.PAN��ʾ��ȡ������Ƶ������FloatControl��
			FloatControl panControl = (FloatControl) clip
					.getControl(FloatControl.Type.PAN);
			if (panControl != null) {
				// ����һ�����������Ľ�����
				this.add(createSlider(panControl), BorderLayout.SOUTH);
			}
		}
        catch(IllegalArgumentException e) { 
        }
    }
    /**
     * ����FloatControl����һ��������
     */
    JSlider createSlider(final FloatControl c) {
        if (c == null){
			return null;
        }
        // ����һ��������
		final JSlider slider = new JSlider(0, 1000);
		// ��ȡFloatControl����Сֵ�����ֵ���͵�ǰֵ
		final float min = c.getMinimum();
		final float max = c.getMaximum();
		final float width = max - min;
		float fval = c.getValue();
		// ���ý������ĵ�ǰֵ
		slider.setValue((int) ((fval - min) / width * 1000));

		// �ڽ�����������3���̶ȣ�����߱�ʾFloatControl��Сֵ�ı�ǩ
		// �м��ʾFloatControl�м�ֵ�ı�ǩ�����ұ߱�ʾFloatControl���ֵ�ı�ǩ
		java.util.Hashtable labels = new java.util.Hashtable(3);
		labels.put(new Integer(0), new JLabel(c.getMinLabel()));
		labels.put(new Integer(500), new JLabel(c.getMidLabel()));
		labels.put(new Integer(1000), new JLabel(c.getMaxLabel()));
		// ����Щ�̶ȱ���ڽ������ϲ���ʾ
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);

		// Ϊ����������һ���б���ı߿�
		// �߿�ı���ΪFloatControl������ �� FloatControl�ĵ�λ����dB
        slider.setBorder(new TitledBorder(c.getType().toString() + " " +
                                     c.getUnits()));

        // Ϊ����������¼������������϶�������ʱ���������¼�
        slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                	// ��ȡ��������ֵ
                    int i = slider.getValue();
                    // ����� FloatControl��ֵ
                    float f = min + (i*width/1000.0f);
                    // ���õ�FloatControl��
                    c.setValue(f);
                }
            });
        return slider;
    }

    /**
     * ����MIDI��Ƶ�������������������ֵ��ٶ�tempo
     * ������ѡ��ѡ���Ƕ���solo����ʹ��������mute
     */
    void addMidiControls() {
        // ���������������ٶ�
		final JSlider tempoSlider = new JSlider(50, 200);
		// ��������ֵ��Ϊ��ǰ��Ƶ���ٶ�
		tempoSlider.setValue((int) (sequencer.getTempoFactor() * 100));
		// ����һ���б���ı߿�
		tempoSlider.setBorder(new TitledBorder("Tempo Adjustment (%)"));
		java.util.Hashtable labels = new java.util.Hashtable();
		// Ϊ����������3���̶ȣ���ǩ�ֱ�Ϊ50%��100%��200%
		labels.put(new Integer(50), new JLabel("50%"));
		labels.put(new Integer(100), new JLabel("100%"));
		labels.put(new Integer(200), new JLabel("200%"));
		// ���̶���ӵ��������ϲ���ʾ
		tempoSlider.setLabelTable(labels);
		tempoSlider.setPaintLabels(true);
		// ����¼�������
		tempoSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequencer.setTempoFactor(tempoSlider.getValue() / 100.0f);
			}
		});

		this.add(tempoSlider, BorderLayout.CENTER);

		//����ȡ��Ƶ�Ĺ��track
		Track[] tracks = sequence.getTracks();
		// ����һ�������壬������Щѡ���
		JPanel trackPanel = new JPanel();
		trackPanel.setLayout(new GridLayout(tracks.length, 1));
		// Ϊÿһ�����track����ѡ�����solo����mute
		for (int i = 0; i < tracks.length; i++) {
			final int tracknum = i;
			// ÿ���������ѡ���
			final JCheckBox solo = new JCheckBox("solo");
			final JCheckBox mute = new JCheckBox("mute");
			// ����¼�������
			solo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Ϊsequencer���ù����soloֵ
					sequencer.setTrackSolo(tracknum, solo.isSelected());
				}
			});
			mute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Ϊsequencer���ù����muteֵ
					sequencer.setTrackMute(tracknum, mute.isSelected());
				}
			});

			// ��ÿ��track�ĸ�ѡ�򶼷���һ�������
			JPanel panel = new JPanel();
			panel.add(new JLabel("Track " + tracknum));
			panel.add(solo);
			panel.add(mute);
			
			trackPanel.add(panel);
		}
		// ��ӵ�������
		this.add(trackPanel, BorderLayout.SOUTH);
	}
}