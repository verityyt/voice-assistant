import win32api
import win32con
import sys

key = str(sys.argv[-1])

if key == 'play' or key == 'pause':
    win32api.keybd_event(win32con.VK_MEDIA_PLAY_PAUSE, 0, win32con.KEYEVENTF_EXTENDEDKEY, 0)
elif key == 'pre':
    win32api.keybd_event(win32con.VK_MEDIA_PREV_TRACK, 0, win32con.KEYEVENTF_EXTENDEDKEY, 0)
elif key == 'next':
    win32api.keybd_event(win32con.VK_MEDIA_NEXT_TRACK, 0, win32con.KEYEVENTF_EXTENDEDKEY, 0)