% Facts with one argument
% Games
game(gta_v).
game(the_witcher_3).
game(minecraft).
game(overwatch).
game(cyberpunk_2077).
game(dark_souls).
game(sekiro).
game(bioshock).
game(red_dead_redemption_2).
game(portal_2).

% Platforms
platform(pc).
platform(ps4).
platform(ps5).
platform(xbox_one).
platform(xbox_series_x).
platform(switch).

% Genres
genre(action).
genre(adventure).
genre(rpg).
genre(shooter).
genre(strategy).
genre(sandbox).

% Characters
character(geralt).
character(trevor).
character(arthur_morgan).
character(jack).
character(aloy).
character(tracer).
character(john_marston).
character(bj_blazkowicz).
character(sekiro).
character(mario).

% Facts with two arguments
% Game and its genre
game_genre(gta_v, action).
game_genre(the_witcher_3, rpg).
game_genre(minecraft, sandbox).
game_genre(overwatch, shooter).
game_genre(cyberpunk_2077, rpg).
game_genre(dark_souls, rpg).
game_genre(sekiro, action).
game_genre(bioshock, shooter).
game_genre(red_dead_redemption_2, action).
game_genre(portal_2, adventure).

% Game and platform
game_platform(gta_v, pc).
game_platform(gta_v, ps4).
game_platform(the_witcher_3, pc).
game_platform(the_witcher_3, ps4).
game_platform(minecraft, pc).
game_platform(overwatch, ps4).
game_platform(cyberpunk_2077, ps4).
game_platform(cyberpunk_2077, xbox_one).
game_platform(dark_souls, ps4).
game_platform(bioshock, pc).
game_platform(red_dead_redemption_2, ps4).
game_platform(portal_2, pc).

% Game and character
game_character(the_witcher_3, geralt).
game_character(gta_v, trevor).
game_character(red_dead_redemption_2, arthur_morgan).
game_character(bioshock, jack).
game_character(overwatch, tracer).
game_character(red_dead_redemption_1, john_marston).
game_character(sekiro, sekiro).

% Rules
available_on_platform(Game, Platform) :- game_platform(Game, Platform). % Игра доступна на платформе игра доступна на платформе, если указано, что она доступна для конкретной платформы.
belongs_to_genre(Game, Genre) :- game_genre(Game, Genre). % Игра относится к жанру игра является частью жанра, если факт жанра связан с игрой
has_character(Game, Character) :- game_character(Game, Character). % Игра имеет персонажа игра имеет персонажа, если персонаж связан с игрой.
multiplatform(Game) :- game_platform(Game, Platform1), game_platform(Game, Platform2), Platform1 \= Platform2. % Мультиплатформенная игра игра является мультиплатформенной, если она доступна более чем на одной платформе.
character_in_genre(Character, Genre) :- game_character(Game, Character), game_genre(Game, Genre). % Игра, содержащая персонажа определённого жанра определяет, есть ли в игре персонаж, если игра относится к определённому жанру

% requests
% 1(empty)
% ?- game(gta_v).
% ?- genre(rpg).

% 2(with params)
% ?- belongs_to_genre(Game, rpg).
% ?- has_character(the_witcher_3, Character).

% 3(logic)
% ?- multiplatform(Game).	
% ?- character_in_genre(Character, rpg).

% 4(hard)
% ?- has_character(Game, geralt); belongs_to_genre(Game, Genre).

% 5(with \+)
% ?- belongs_to_genre(Game, Genre), Genre \= rpg.
