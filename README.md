# Simple List Service
Very simple data service for caseworking systems

---

# Endpoints
## POST:  /list

#### Description:
Creates a new list and child entities, supporting a dynamic number of sub entities. Additional properties can be added to the entity and sub entity objects.

#### Example request body: 
```json
	{
		"name": "Topics",
		"entities" : [
			{
				"text": "Topic List 1",
				"value": "Topic Group 1",
				"properties": [
					{
						"key": "Topic List Property",
						"value": "Topic List Property Value"
					}
				],
				"subEntities": [
					{
						"text": "Topic 1",
						"value": "Topic Value 1",
						"properties": [
							{
								"key": "Topic Property 1",
								"value": "Topic Property Value 1"								
							}
						]
					}, 
					{
                        "text": "Topic 2",
                        "value": "Topic Value 2",
                        "properties": [
                            {
                                "key": "Topic Property 2",
                                "value": "Topic Property Value 2"                                
                            }
                        ]
                    }
				]
			}, 
			{
				"text": "Topic List 2",
				"value": "Topic Group 2"
			}
		]
	}
```
## GET: /list/{name}

#### Description:
Returns a list resource from the service by name.

#### Example response body: 
```json
{
    "name": "Topics",
    "entities": [
        {
            "text": "Topic List 2",
            "value": "Topic Group 2"
        },
        {
            "text": "Topic List 1",
            "value": "Topic Group 1",
            "subEntities": [
                {
                    "text": "Topic 2",
                    "value": "Topic Value 2",
                    "properties": [
                        {
                            "key": "Topic Property 2",
                            "value": "Topic Property Value 2"
                        }
                    ]
                },
                {
                    "text": "Topic 1",
                    "value": "Topic Value 1",
                    "properties": [
                        {
                            "key": "Topic Property 1",
                            "value": "Topic Property Value 1"
                        }
                    ]
                }
            ],
            "properties": [
                {
                    "key": "Topic List Property",
                    "value": "Topic List Property Value"
                }
            ]
        }
    ]
}
```

## GET: /legacy/list/{name}

#### Description:
Returns a legacy list resource from the service by name.

#### Example response body: 
```json
[
    {
        "name": "Topic List 1",
        "caseType": "Topic Group 1",
        "topicListItems": [
            {
                "topicName": "Topic 1",
                "topicUnit": "Topic Value 1",
                "Topic Property 1": "Topic Property Value 1"
            },
            {
                "topicName": "Topic 2",
                "topicUnit": "Topic Value 2",
                "Topic Property 2": "Topic Property Value 2"
            }
        ]
    },
    {
        "name": "Topic List 2",
        "caseType": "Topic Group 2",
        "topicListItems": []
    }
]
```


