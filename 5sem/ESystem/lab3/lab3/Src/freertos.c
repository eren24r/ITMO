
#include "FreeRTOS.h"
#include "task.h"
#include "main.h"
#include "cmsis_os.h"

#include "usart.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "kb.h"
#include "fonts.h"
#include "oled.h"

typedef struct {
    uint8_t x;
    uint8_t y;
} Point;

typedef enum {
    UP,
    DOWN,
    LEFT,
    RIGHT
} Direction;

typedef struct {
    Point body[20];
    uint8_t length;
    Direction direction;
} Snake;

#define SNAKE_MAX_LENGTH 20
#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64
#define GRID_X 7
#define GRID_Y 10
#define GRID_SIZE 8
#define SNAKE_HEAD_CHAR '@'
#define SNAKE_BODY_CHAR '#'
#define FOOD_CHAR '*'

Snake snake;
Point food;
Point prevPoint;
int gameOver = 0;
/* USER CODE END Variables */
/* Definitions for oledTask */
osThreadId_t oledTaskHandle;
const osThreadAttr_t oledTask_attributes = {
  .name = "oledTask",
  .stack_size = 128 * 4,
  .priority = (osPriority_t) osPriorityNormal,
};
/* Definitions for snakeGoTask */
osThreadId_t snakeGoTaskHandle;
const osThreadAttr_t snakeGoTask_attributes = {
  .name = "snakeGoTask",
  .stack_size = 128 * 4,
  .priority = (osPriority_t) osPriorityNormal,
};
/* Definitions for IOTask */
osThreadId_t IOTaskHandle;
const osThreadAttr_t IOTask_attributes = {
  .name = "IOTask",
  .stack_size = 128 * 4,
  .priority = (osPriority_t) osPriorityNormal,
};
/* Definitions for snakeMutex */
osMutexId_t snakeMutexHandle;
const osMutexAttr_t snakeMutex_attributes = {
  .name = "snakeMutex"
};

/* Private function prototypes -----------------------------------------------*/
/* USER CODE BEGIN FunctionPrototypes */
void InitGame(void);
void UpdateSnakePosition(void);

/* USER CODE END FunctionPrototypes */

void StartOledTask(void *argument);
void StartSnakeGoTask(void *argument);
void StartIOTask(void *argument);

void MX_FREERTOS_Init(void); /* (MISRA C 2004 rule 8.1) */

/* Hook prototypes */
void configureTimerForRunTimeStats(void);
unsigned long getRunTimeCounterValue(void);

/* USER CODE BEGIN 1 */
/* Functions needed when configGENERATE_RUN_TIME_STATS is on */
__weak void configureTimerForRunTimeStats(void)
{

}

__weak unsigned long getRunTimeCounterValue(void)
{
return 0;
}

void MX_FREERTOS_Init(void) {

	InitGame();

  snakeMutexHandle = osMutexNew(&snakeMutex_attributes);


  oledTaskHandle = osThreadNew(StartOledTask, NULL, &oledTask_attributes);

  snakeGoTaskHandle = osThreadNew(StartSnakeGoTask, NULL, &snakeGoTask_attributes);


  IOTaskHandle = osThreadNew(StartIOTask, NULL, &IOTask_attributes);


}

void StartOledTask(void *argument)
{
	for (;;) {
	    if (!gameOver) {
	    	oled_Reset();
	        for (int i = 0; i < snake.length; i++) {
	        	oled_SetCursor(snake.body[i].x * GRID_X, snake.body[i].y * GRID_Y);
				if (i == 0) {
					oled_WriteChar(SNAKE_HEAD_CHAR, Font_7x10, White);
				} else {
					oled_WriteChar(SNAKE_BODY_CHAR, Font_7x10, White);
				}
	        }
	        oled_SetCursor(food.x * GRID_X, food.y * GRID_Y);
	        oled_WriteChar(FOOD_CHAR, Font_7x10, White);
	        oled_UpdateScreen();
	    } else {
	    	oled_Reset();
	    	oled_WriteString("Game Over!", Font_7x10, White);
	    	oled_UpdateScreen();
	    }
	    osDelay(100);
	}
}

void StartSnakeGoTask(void *argument)
{
  for(;;)
  {
	  if (!gameOver) {
		  osMutexAcquire(snakeMutexHandle, osWaitForever);
		  UpdateSnakePosition();
		  osMutexRelease(snakeMutexHandle);
	  }
	  osDelay(500);
  }

}

void StartIOTask(void *argument)
{
  /* USER CODE BEGIN StartIOTask */
	uint8_t Key;
	/* Infinite loop */
  for(;;)
  {
	  Key = Check_Row();

	  if (Key == 0x0A && snake.direction != RIGHT) {
		  osMutexAcquire(snakeMutexHandle, osWaitForever);
		  snake.direction = LEFT;
		  osMutexRelease(snakeMutexHandle);
	  }
	  if (Key == 0x30 && snake.direction != UP)  {
		  osMutexAcquire(snakeMutexHandle, osWaitForever);
		  snake.direction = DOWN;
		  osMutexRelease(snakeMutexHandle);
	  }
      if (Key == 0x38 && snake.direction != DOWN)  {
    	  osMutexAcquire(snakeMutexHandle, osWaitForever);
    	  snake.direction = UP;
    	  osMutexRelease(snakeMutexHandle);
      }
      if (Key == 0x0E && snake.direction != LEFT) {
    	  osMutexAcquire(snakeMutexHandle, osWaitForever);
    	  snake.direction = RIGHT;
    	  osMutexRelease(snakeMutexHandle);
      }
	  osDelay(25);
  }

}


void InitGame(void) {
    snake.length = 3;
    snake.body[0] = (Point){5, 5};
    snake.body[1] = (Point){4, 5};
    snake.body[2] = (Point){3, 5};
    snake.direction = RIGHT;

    food.x = rand() % (SCREEN_WIDTH / GRID_X);
    food.y = rand() % (SCREEN_HEIGHT / GRID_Y);
    gameOver = 0;
}

void UpdateSnakePosition(void) {
	prevPoint.x = snake.body[snake.length - 1].x;
	prevPoint.y = snake.body[snake.length - 1].y;
    for (int i = snake.length - 1; i > 0; i--) {
        snake.body[i] = snake.body[i - 1];
    }

    switch (snake.direction) {
        case UP:    snake.body[0].y -= 1; break;
        case DOWN:  snake.body[0].y += 1; break;
        case LEFT:  snake.body[0].x -= 1; break;
        case RIGHT: snake.body[0].x += 1; break;
    }

    if (snake.body[0].x < 0 || snake.body[0].x >= SCREEN_WIDTH / GRID_X ||
        snake.body[0].y < 0 || snake.body[0].y >= SCREEN_HEIGHT / GRID_Y ) {
        gameOver = 1;
    }

    for (int i = 1; i < snake.length; i++) {
        if (snake.body[0].x == snake.body[i].x && snake.body[0].y == snake.body[i].y) {
            gameOver = 1;
        }
    }

    if (snake.body[0].x == food.x && snake.body[0].y == food.y) {
        snake.length++;
        snake.body[snake.length - 1].x = prevPoint.x;
        snake.body[snake.length - 1].y = prevPoint.y;
        food.x = rand() % (SCREEN_WIDTH / GRID_X);
        food.y = rand() % (SCREEN_HEIGHT / GRID_Y);
    }
}

