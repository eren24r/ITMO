import cv2
import numpy as np
from playsound import playsound

def detect_color(hsv_frame, start_point, end_point):
    """
    Определяет, какой цвет отображается в заданной области кадра.
    """
    color_ranges = {
        "red": ([0, 50, 50], [10, 255, 255]),
        "orange": ([11, 50, 50], [25, 255, 255]),
        "yellow": ([26, 50, 50], [35, 255, 255]),
        "green": ([36, 50, 50], [85, 255, 255]),
        "blue": ([86, 50, 50], [125, 255, 255]),
        "indigo": ([126, 50, 50], [145, 255, 255]),
        "violet": ([146, 50, 50], [160, 255, 255])
    }

    for color_name, (lower, upper) in color_ranges.items():
        lower = np.array(lower)
        upper = np.array(upper)
        mask = cv2.inRange(hsv_frame[start_point[1]:end_point[1], start_point[0]:end_point[0]], lower, upper)
        color_rate = np.count_nonzero(mask) / ((end_point[1] - start_point[1]) * (end_point[0] - start_point[0]))
        if color_rate > 0.6:
            return color_name
    return None

def main():
    correct_sequence = ["red", "blue", "yellow", "green"]
    cap = cv2.VideoCapture(0)

    while True:
        captured_sequence = []
        while len(captured_sequence) < 4:
            ret, frame = cap.read()
            if not ret:
                break

            hsv_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

            height, width, _ = frame.shape
            rect_size = 100
            offsets = [
                (int(width / 5 - rect_size / 4 - 20), int(height / 2 - rect_size / 2)),
                (int(width / 2.4 - rect_size / 2), int(height / 2 - rect_size / 2)),
                (int(2.4 * width / 3 - rect_size / 2 - 110), int(height / 2 - rect_size / 2)),
                (int(2.8 * width / 3 - rect_size / 2 - 60), int(height / 2 - rect_size / 2))
            ]

            for i, (x, y) in enumerate(offsets):
                start_point = (x, y)
                end_point = (x + rect_size, y + rect_size)
                cv2.rectangle(frame, start_point, end_point, (255, 255, 255), 2)

                if len(captured_sequence) <= i:
                    detected_color = detect_color(hsv_frame, start_point, end_point)
                    if detected_color:
                        if len(captured_sequence) > i:
                            captured_sequence[i] = detected_color
                        else:
                            captured_sequence.append(detected_color)

            cv2.putText(frame, f"Current sequence: {', '.join(captured_sequence)}", (10, 30),
                        cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2, cv2.LINE_AA)
            cv2.imshow("Camera", frame)

            if cv2.waitKey(30) & 0xFF == 27:
                cap.release()
                cv2.destroyAllWindows()
                return

        if captured_sequence == correct_sequence:
            print("Access is allowed!")
            playsound('mp3.mp3')
            break

    cap.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()