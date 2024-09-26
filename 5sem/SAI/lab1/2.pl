% Классы
% Game — для игр
% Genre — для жанров
% Platform — для платформ
% Character — для персонажей

% Объектные свойства
% belongsToGenre — указывает, к какому жанру относится игра.
% availableOnPlatform — указывает, на какой платформе доступна игра.
% hasCharacter — указывает, какие персонажи есть в игре.

% Индивидуумы
% Например, gta_v — это индивидуум класса Game, который относится к жанру action, доступен на платформах pc и ps4

% SPARQL-запросы
% Игры жанра RPG
SELECT ?game WHERE {
  ?game rdf:type :Game .
  ?game :belongsToGenre :rpg .
}

% Игры на платформе PC
SELECT ?game WHERE {
  ?game rdf:type :Game .
  ?game :availableOnPlatform :pc .
}

% Найти игры, доступные на нескольких платформах
SELECT ?game WHERE {
    ?game rdf:type :Game .
    ?game :availableOnPlatform ?platform1 .
    ?game :availableOnPlatform ?platform2 .
    FILTER (?platform1 != ?platform2)
}


