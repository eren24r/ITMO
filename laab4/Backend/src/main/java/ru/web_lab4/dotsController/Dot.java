package ru.web_lab4.dotsController;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class Dot implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Float x;
    private Float y;
    private Float r;
    private Boolean status;
    private String time;
    private Long scriptTime;
    private String ownerLogin;

    public Dot(float x, float y, float r, String ownerLogin){
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = checkStatus(x, y, r);
        this.time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        this.ownerLogin = ownerLogin;
    }

    public static boolean validateInput(float x, float y, float r){
        if (x > 3 || x < -3) return false;
        if (y > 3 || y < -3) return false;
        if (r > 3 || r < 0) return false;

        return true;
    }

    public static boolean checkStatus(float x, float y, float r){
        if (x <= 0 && y <= 0 && y >= -r && x >= -r) {
            return true;
        }

        // Проверка прямоугольного треугольника (верхняя правая часть)
        if (x >= 0 && x <= r && y >= -r / 2 && y <= 0) {
            float x1 = 0, y1 = 0;
            float x2 = r, y2 = 0;
            float x3 = 0, y3 = -r / 2;

            // Функция для вычисления площади треугольника по координатам
            float area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0f);

            // Площади подтреугольников с точкой (x, y)
            float area1 = Math.abs((x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2)) / 2.0f);
            float area2 = Math.abs((x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y)) / 2.0f);
            float area3 = Math.abs((x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2)) / 2.0f);

            // Точка внутри, если сумма подплощадей равна площади треугольника
            return (area == (area1 + area2 + area3));

        }

        // Проверка четверти окружности (нижняя левая часть)
        if (x >= 0 && y >= 0 && (x * x + y * y <= (r) * (r))){
            return true;
        }

        return false;
    }
}
