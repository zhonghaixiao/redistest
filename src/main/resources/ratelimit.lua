local key = "rate.limit:" .. KEYS[1]
local limit = tonumber(ARGV[1])
local expire_time = ARGV[2]

--local result = {}
--local ids = {}
--local activityIds = redis.call("zrevrange", "ec-activity-ids:"..KEYS[1], 0, -1)
--for i,activityId in ipairs(activityIds) do
--    local activity = redis.call("get", "ec-activity:"..activityId)
--    result[i] = activity
--    ids[i] = activityId
--end
--return {result, ids}


local is_exists = redis.call("EXISTS", key)
if is_exists == 1 then
    if redis.call("INCR", key) > limit then
        return 0
    else
        return 1
    end
else
    redis.call("SET", key, 1)
    redis.call("EXPIRE", key, expire_time)
    return 1
end


