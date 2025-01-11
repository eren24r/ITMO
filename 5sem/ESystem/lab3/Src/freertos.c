/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * File Name          : freertos.c
  * Description        : Code for freertos applications
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2024 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */

/* Includes ------------------------------------------------------------------*/
#include "FreeRTOS.h"
#include "task.h"
#include "main.h"
#include "cmsis_os.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */
#include "usart.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "kb.h"
#include "fonts.h"
#include "oled.h"
/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */
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
/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */
#define SNAKE_MAX_LENGTH 20
#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64
#define GRID_X 7
#define GRID_Y 10
#define GRID_SIZE 8
#define SNAKE_HEAD_CHAR '@'
#define SNAKE_BODY_CHAR '#'
#define FOOD_CHAR '*'
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/
/* USER CODE BEGIN Variables */
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
/* USER CODE END 1 */

/**
  * @brief  FreeRTOS initialization
  * @param  None
  * @retval None
  */
void MX_FREERTOS_Init(void) {
  /* USER CODE BEGIN Init */
	InitGame();
  /* USER CODE END Init */
  /* Create the mutex(es) */
  /* creation of snakeMutex */
  snakeMutexHandle = osMutexNew(&snakeMutex_attributes);

  /* USER CODE BEGIN RTOS_MUTEX */
  /* add mutexes, ... */
  /* USER CODE END RTOS_MUTEX */

  /* USER CODE BEGIN RTOS_SEMAPHORES */
  /* add semaphores, ... */
  /* USER CODE END RTOS_SEMAPHORES */

  /* USER CODE BEGIN RTOS_TIMERS */
  /* start timers, add new ones, ... */
  /* USER CODE END RTOS_TIMERS */

  /* USER CODE BEGIN RTOS_QUEUES */
  /* add queues, ... */
  /* USER CODE END RTOS_QUEUES */

  /* Create the thread(s) */
  /* creation of oledTask */
  oledTaskHandle = osThreadNew(StartOledTask, NULL, &oledTask_attributes);

  /* creation of snakeGoTask */
  snakeGoTaskHandle = osThreadNew(StartSnakeGoTask, NULL, &snakeGoTask_attributes);

  /* creation of IOTask */
  IOTaskHandle = osThreadNew(StartIOTask, NULL, &IOTask_attributes);

  /* USER CODE BEGIN RTOS_THREADS */
  /* add threads, ... */
  /* USER CODE END RTOS_THREADS */

  /* USER CODE BEGIN RTOS_EVENTS */
  /* add events, ... */
  /* USER CODE END RTOS_EVENTS */

}

/* USER CODE BEGIN Header_StartOledTask */
/**
  * @brief  Function implementing the oledTask thread.
  * @param  argument: Not used
  * @retval None
  */
/* USER CODE END Header_StartOledTask */
void StartOledTask(void *argument)
{
  /* USER CODE BEGIN StartOledTask */
  /* Infinite loop */
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
  /* USER CODE END StartOledTask */
}

/* USER CODE BEGIN Header_StartSnakeGoTask */
/**
* @brief Function implementing the snakeGoTask thread.
* @param argument: Not used
* @retval None
*/
/* USER CODE END Header_StartSnakeGoTask */
void StartSnakeGoTask(void *argument)
{
  /* USER CODE BEGIN StartSnakeGoTask */
  /* Infinite loop */
  for(;;)
  {
	  if (!gameOver) {
		  osMutexAcquire(snakeMutexHandle, osWaitForever);
		  UpdateSnakePosition();
		  osMutexRelease(snakeMutexHandle);
	  }
	  osDelay(500);
  }
  /* USER CODE END StartSnakeGoTask */
}

/* USER CODE BEGIN Header_StartIOTask */
/**
* @brief Function implementing the IOTask thread.
* @param argument: Not used
* @retval None
*/
/* USER CODE END Header_StartIOTask */
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
  /* USER CODE END StartIOTask */
}

/* Private application code --------------------------------------------------*/
/* USER CODE BEGIN Application */
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

/* USER CODE END Application */

