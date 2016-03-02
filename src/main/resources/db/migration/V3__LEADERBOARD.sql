CREATE VIEW leaderboard AS
SELECT
    socialusername,
    username,
    COUNT(q.submittedbyuserid) AS questionsamount
FROM player p
LEFT JOIN question q ON p.id = q.submittedbyuserid
GROUP BY
    socialusername,
    username
ORDER BY
    questionsamount desc;