package beatsaver

import "time"

type MapDetail struct {
	Id              string            `json:"id"`
	Name            string            `json:"name"`
	Description     string            `json:"description"`
	Uploaded        time.Time         `json:"uploaded"`
	Ranked          bool              `json:"ranked"`
	Qualified       bool              `json:"qualified"`
	CreatedAt       time.Time         `json:"createdAt"`
	UpdatedAt       time.Time         `json:"updatedAt"`
	LastPublishedAt time.Time         `json:"lastPublishedAt"`
	Automapper      bool              `json:"automapper"`
	Uploader        UserDetail        `json:"uploader"`
	Metadata        MapDetailMetadata `json:"metadata"`
	Stats           MapStats          `json:"stats"`
	Versions        []MapVersion      `json:"versions"`
}

type UserDetail struct {
	Id          int32     `json:"id"`
	Name        string    `json:"name"`
	UniqueSet   bool      `json:"uniqueSet"`
	Hash        string    `json:"hash"`
	Avatar      string    `json:"avatar"`
	Type        string    `json:"type"` // enum
	Testplay    bool      `json:"testplay,omitempty"`
	Stats       UserStats `json:"stats,omitempty"`
	UploadLimit int32     `json:"uploadLimit,omitempty"`
}

const (
	UserTypeDISCORD = "DISCORD"
	UserTypeSIMPLE  = "SIMPLE"
	UserTypeDUAL    = "DUAL"
)

type MapDetailMetadata struct {
	Bpm             float32 `json:"bpm"`
	Duration        int32   `json:"duration"`
	SongName        string  `json:"songName"`
	SongSubName     string  `json:"songSubName"`
	SongAuthorName  string  `json:"songAuthorName"`
	LevelAuthorName string  `json:"levelAuthorName"`
}

type MapStats struct {
	Plays     int32   `json:"plays"`
	Downloads int32   `json:"downloads"`
	Upvotes   int32   `json:"upvotes"`
	Downvotes int32   `json:"downvotes"`
	Score     float32 `json:"score"`
}

type MapVersion struct {
	Hash        string          `json:"hash"`
	Key         string          `json:"key"`
	State       string          `json:"state"` // enum
	CreatedAt   time.Time       `json:"createdAt"`
	SageScore   int16           `json:"sageScore"`
	Diffs       []MapDifficulty `json:"diffs"`
	DownloadURL string          `json:"downloadURL"`
	CoverURL    string          `json:"coverURL"`
	PreviewURL  string          `json:"previewURL"`
	TestplayAt  time.Time       `json:"testplayAt,omitempty"`
	Testplays   []MapTestplay   `json:"testplays,omitempty"`
}

const (
	MapStateUploaded  = "Uploaded"
	MapStateTestplay  = "Testplay"
	MapStatePublished = "Published"
	MapStateFeedback  = "Feedback"
)

type MapDifficulty struct {
	Njs            float32          `json:"njs"`
	Offset         float32          `json:"offset"`
	Notes          int32            `json:"notes"`
	Bombs          int32            `json:"bombs"`
	Obstacles      int32            `json:"obstacles"`
	Nps            float64          `json:"nps"`
	Length         float64          `json:"length"`
	Characteristic string           `json:"characteristic"` // enum
	Difficulty     string           `json:"difficulty"`     // enum
	Events         int32            `json:"events"`
	Chroma         bool             `json:"chroma"`
	Me             bool             `json:"me"`
	Ne             bool             `json:"ne"`
	Cinema         bool             `json:"cinema"`
	Seconds        float64          `json:"seconds"`
	ParitySummary  MapParitySummary `json:"paritySummary"`
	Stars          float32          `json:"stars,omitempty"`
}

const (
	DifficultyEasy       = "Easy"
	DifficultyNormal     = "Normal"
	DifficultyHard       = "Hard"
	DifficultyExpert     = "Expert"
	DifficultyExpertPlus = "ExpertPlus"

	CharacteristicStandard  = "Standard"
	CharacteristicOneSaber  = "OneSaber"
	CharacteristicNoArrows  = "NoArrows"
	Characteristic360Degree = "360Degree"
	Characteristic90Degree  = "90Degree"
	CharacteristicLawless   = "Lawless"
	CharacteristicLightshow = "Lightshow"
)

type MapParitySummary struct {
	Errors int32 `json:"errors"`
	Warns  int32 `json:"warns"`
	Resets int32 `json:"resets"`
}

type MapTestplay struct {
	CreatedAt  time.Time  `json:"createdAt"`
	Feedback   string     `json:"feedback"`
	FeedbackAt time.Time  `json:"feedbackAt"`
	User       UserDetail `json:"user"`
	Video      string     `json:"video"`
}

type UserStats struct {
	AvgBpm         float32       `json:"avgBpm"`
	AvgDuration    float32       `json:"avgDuration"`
	AvgScore       float32       `json:"avgScore"`
	DiffStats      UserDiffStats `json:"diffStats"`
	FirstUpload    time.Time     `json:"firstUpload"`
	LastUpload     time.Time     `json:"lastUpload"`
	RankedMaps     int32         `json:"rankedMaps"`
	TotalDownvotes int32         `json:"totalDownvotes"`
	TotalMaps      int32         `json:"totalMaps"`
	TotalUpvotes   int32         `json:"totalUpvotes"`
}

type UserDiffStats struct {
	Easy       int32 `json:"easy"`
	Expert     int32 `json:"expert"`
	ExpertPlus int32 `json:"expertPlus"`
	Hard       int32 `json:"hard"`
	Normal     int32 `json:"normal"`
	Total      int32 `json:"total"`
}

type SearchResponse struct {
	Docs     []MapDetail `json:"docs"`
	Redirect string      `json:"redirect"`
}

type AuthRequest struct {
	OculusId string `json:"oculusId"`
	Proof    string `json:"proof"`
	SteamId  string `json:"steamId"`
}

type Response struct {
	Error   string `json:"error"`
	Success bool   `json:"success"`
}

type VoteResponse Response
type VerifyResponse Response

// ListOfVoteSummary?

type VoteSummary struct {
	Downvotes int32   `json:"downvotes"`
	Hash      string  `json:"hash"`
	Key64     string  `json:"key64"`
	MapId     int32   `json:"mapId"`
	Score     float32 `json:"score"`
	Upvotes   int32   `json:"upvotes"`
}

type VoteRequest struct {
	Auth      AuthRequest `json:"auth"`
	Direction bool        `json:"direction"`
	Hash      string      `json:"hash"`
}
