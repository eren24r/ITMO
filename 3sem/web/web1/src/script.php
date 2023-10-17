<?php
header("Access-Control-Allow-Origin: *");
// Функция для проверки, попадает ли точка в область
function isPointInArea($x, $y, $r)
{
    // Triangle
    if ($x <= 0 && $x >= -$r && $y >= 0 && $y <= $r) {
        $newX = -$r + $y;
        if($newX <= $x){
            return true;
        }
    }
    // Rectangle 
    if ($x >= 0 && $x <= $r / 2 && $y >= 0 && $y <= $r) {
        return true;
    }
    // Circle
    if ($x >= 0 && $x <= $r / 2 && $y <= 0 && $y >= -$r / 2) {
        return ($x * $x + $y * $y) <= ($r * $r);
    }

    // For bottom-left quadrant, always return false
    return false;
}

// Получаем параметры R и координаты точки из GET-запроса
if (isset($_GET['r']) && isset($_GET['x']) && isset($_GET['y'])) {
    $start_date = new DateTimeImmutable();
    $start = (int)$start_date->format('Uu');

    $r = floatval($_GET['r']);
    $x = floatval($_GET['x']);
    $y = floatval($_GET['y']);

    // Валидация данных
    if ($r > 0 && $y >= -3 && $y <= 3) {
        // Проверка попадания точки в область
        $isInArea = isPointInArea($x, $y, $r);
        $currentTime = date('Y-m-d H:i:s');

        $finish_date = new DateTimeImmutable();
        $finish = (int)$finish_date->format('Uu');

        $executionTime = $finish - $start;;

        // Отправляем результат в формате JSON
        header('Content-Type: application/json');
        echo json_encode([
            'r' => $r,
            'x' => $x,
            'y' => $y,
            'result' => $isInArea,
            'time' => $currentTime,
            'execution_time' => $executionTime
        ]);
    } else {
        echo 'Некорректное значение радиуса R';
    }
} else {
    echo 'Не все параметры переданы';
}

